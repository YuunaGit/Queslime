package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.UserService;
import com.queslime.utils.Encoder;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

public class LoginController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public Result loginUser(@RequestParam(value = "email", defaultValue = "")String userEmail,
                            @RequestParam(value = "password", defaultValue = "")String userPassword) {
        Result result = new Result();

        if("".equals(userEmail)) {
            return result.info(Info.EMAIL_NULL);
        }

        if("".equals(userPassword)) {
            return result.info(Info.PWD_NULL);
        }

        User user = userService.selectOneByEmail(userEmail);

        if(user == null) {
            return result.info(Info.LOGIN_EMAIL_OR_PWD_WRONG);
        }

        if(Encoder.match(userPassword, user.getUserPassword())) {
            return result.info(Info.LOGIN_SUCCESS);
        }

        return result.info(Info.LOGIN_EMAIL_OR_PWD_WRONG);
    }

}
