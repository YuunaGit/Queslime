package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.enums.entityEnum.UserStatus;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MailController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/activate")
    public Result accountActivate(@RequestParam(value = "uid", defaultValue = "")String uidString) {
        Result result = new Result(null);

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

        





        return result;
    }
}
