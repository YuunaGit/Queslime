package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.UserService;
import com.queslime.utils.Encoder;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class RegisterController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/register")
    public Result registerUser(@RequestParam(value = "email", defaultValue = "")String userEmail,
                               @RequestParam(value = "password", defaultValue = "")String userPassword) {
        Result result = new Result();

        if("".equals(userEmail)) {
            return result.info(Info.EMAIL_NULL);
        }

        if("".equals(userPassword)) {
            return result.info(Info.PWD_NULL);
        }

        if(userEmail.length() >= 100) {
            return result.info(Info.EMAIL_TOO_LONG);
        }

        if(userService.isEmailIllegal(userEmail)) {
            return result.info(Info.EMAIL_ILLEGAL);
        }

        if(userService.isPasswordIllegal(userPassword)) {
            return result.info(Info.PWD_ILLEGAL);
        }

        if(userService.isEmailDuplicate(userEmail)) {
            return result.info(Info.EMAIL_DUPLICATE);
        }

        User newUser = new User(
                userService.generatedUserName(),
                userEmail,
                Encoder.encode(userPassword)
        );

        if(userService.insert(newUser) == 0) {
            return result.info(Info.FAIL);
        }

        return result.info(Info.SUCCESS);
    }
}