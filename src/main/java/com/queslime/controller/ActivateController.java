package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.enums.entityEnum.UserState;
import com.queslime.service.CodeCache;
import com.queslime.service.UserService;
import com.queslime.utils.EmailSender;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class ActivateController {
    @Resource
    private UserService userService;
    @Resource
    private EmailSender emailSender;
    @Resource
    private CodeCache codeCache;

    @RequestMapping(value = "/activate/user")
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

        if(user.getUserState() != UserState.NOT_ACTIVATED) {
            return result.info(Info.ALREADY_ACTIVATED);
        }

        int key = codeCache.generateRandomCode();
        codeCache.putCode(uid, Integer.toString(key));
        uid ^= key;
        String code = Long.toHexString((long) key << 32 | uid);

        emailSender.sendMail(
                user.getUserEmail(),
                "欢迎使用Queslime",
                "请点击下方链接激活账号吧！\n\nhttp://localhost:9090/activate/check?code=" + code
        );

        return result.info(Info.EMAIL_SEND);
    }

    @RequestMapping(value = "/activate/check")
    public Result accountActivate(@RequestParam(value = "code", defaultValue = "")String codeString) {
        Result result = new Result();

        long code;
        try {
            code = Long.parseLong(codeString, 16);
        } catch (NumberFormatException e) {
            return result.info(Info.FAIL);
        }

        int uid = (int) code;
        int key = (int) (code >> 32);
        uid ^= key;

        User user = userService.selectOneByUid(uid);

        if(user == null) {
            return result.info(Info.FAIL);
        }

        if(codeCache.validateCode(uid, Integer.toString(key))) {
            user.setUserState(UserState.NORMAL);
            if (userService.update(user) == 0) {
                return result.info(Info.FAIL);
            }
        }

        return result.info(Info.SUCCESS);
    }
}
