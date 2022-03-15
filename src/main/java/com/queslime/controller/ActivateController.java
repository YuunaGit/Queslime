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

@RestController
public class ActivateController {
    @Resource
    private UserService userService;
    @Resource
    private EmailSender emailSender;

    @RequestMapping(value = "/user/activate")
    public Result accountActivateSendEmail(@RequestParam(value = "uid", defaultValue = "")String uidString) {
        Result result = new Result();

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        if(user.getUserStatus() != UserStatus.NOT_ACTIVATED) {
            return result.info(Info.ACTIVATE_ALREADY_ACTIVATED);
        }

        int createdAt = user.getCreatedAt().getNanos();
        uid ^= createdAt;
        String code = Long.toHexString((long) createdAt << 32 | uid);

        emailSender.sendMail(
                user.getUserEmail(),
                "欢迎使用Queslime",
                "请点击下方链接激活账号吧！\n\n" +
                        "http://localhost:9090/user/activate/verify?code=" + code
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
            return result.info(Info.FAIL);
        }

        int uid = (int) code;
        int createdAt = (int) (code >> 32);
        uid ^= createdAt;

        User user = userService.selectOneByUid(uid);

        if(user == null) {
            return result.info(Info.FAIL);
        }

        if(createdAt == user.getCreatedAt().getNanos()) {
            user.setUserStatus(UserStatus.NORMAL);
            if (userService.update(user) != 0) {
                return result.info(Info.SUCCESS);
            }
        }
        return result.info(Info.FAIL);
    }
}
