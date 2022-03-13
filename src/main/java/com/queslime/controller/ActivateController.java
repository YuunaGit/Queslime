package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.enums.entityEnum.UserStatus;
import com.queslime.service.UserService;
import com.queslime.utils.EmailSender;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RestController
public class ActivateController {
    @Resource
    private UserService userService;
    @Resource
    private EmailSender emailSender;

    @RequestMapping(value = "/user/activate")
    public Result accountActivateSendEmail(@RequestParam(value = "uid", defaultValue = "")String uidString) {
        Result result = new Result();

        int uid;
        try {
            uid = Integer.parseInt(uidString);
        } catch (NumberFormatException e) {
            return result.info(Info.ACTIVATE_UID_ILLEGAL);
        }

        if(uid < 1) {
            return result.info(Info.ACTIVATE_UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.ACTIVATE_UID_NOT_EXISTS);
        }

        if(user.getUserStatus() != UserStatus.NOT_ACTIVATED) {
            return result.info(Info.ACTIVATE_ALREADY_ACTIVATED);
        }

        emailSender.sendMail(
                user.getUserEmail(),
                "欢迎使用Queslime",
                "请点击下方链接激活账号吧！\n\n" +
                        "http://localhost:9090/user/activate/verify?code=" +
                        generatedActivateCode(user)
        );

        return result.info(Info.ACTIVATE_EMAIL_SEND);
    }

    @RequestMapping(value = "/user/activate/verify")
    public Result accountActivate(@RequestParam(value = "code", defaultValue = "")String codeString) {
        Result result = new Result();

        long code;
        try {
            code = Long.parseLong(codeString, 16);
        } catch (NumberFormatException e) {
            return result.info(Info.ACTIVATE_FAIL);
        }

        int uid = (int) (code & (long) -1);
        int createdAt = (int) (code >> 32);
        uid ^= createdAt;

        User user = userService.selectOneByUid(uid);

        if(createdAt == user.getCreatedAt().getNanos()) {
            user.setUserStatus(UserStatus.NORMAL);
            if (userService.update(user) != 0) {
                return result.info(Info.ACTIVATE_SUCCESS);
            }
        }
        return result.info(Info.ACTIVATE_FAIL);
    }

    private String generatedActivateCode(User user) {
        int uid = user.getUid();
        int createdAt = user.getCreatedAt().getNanos();
        uid ^= createdAt;
        return Long.toHexString((long) createdAt << 32 | uid);
    }
}
