package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.UserService;
import com.queslime.utils.Encoder;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@RestController
public class RegisterController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/register")
    public Result registerUser(@RequestParam(value = "email", defaultValue = "")String userEmail,
                               @RequestParam(value = "password", defaultValue = "")String userPassword) {
        Result result = new Result(null);

        if(isEmailIllegal(userEmail)) {
            result.setInfo(Info.REGISTER_EMAIL_ILLEGAL);
            return result;
        }

        if(isEmailDuplicate(userEmail)) {
            result.setInfo(Info.REGISTER_EMAIL_DUPLICATE);
            return result;
        }

        if(isPasswordIllegal(userPassword)) {
            result.setInfo(Info.REGISTER_PWD_ILLEGAL);
            return result;
        }

        User newUser = new User(
                generatedName(),
                userEmail,
                Encoder.encode(userPassword)
        );

        if(userService.insert(newUser) != 0) {
            result.setInfo(Info.REGISTER_SUCCESS);
        } else {
            result.setInfo(Info.REGISTER_FAIL);
        }
        return result;
    }

    private boolean isEmailIllegal(String email) {
        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        return !emailRegex
                .matcher(email)
                .matches();
    }

    public boolean isEmailDuplicate(String email) {
        return userService.selectOneWhereEmailEquals(email) != null;
    }

    private boolean isPasswordIllegal(String pwd) {
        Pattern pwdRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$");
        return !pwdRegex
                .matcher(pwd)
                .matches();
    }

    private String generatedName() {
        long currentTime = System.currentTimeMillis();
        return "User_" + currentTime;
    }
}