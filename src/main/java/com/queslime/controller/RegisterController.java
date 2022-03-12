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

        if("".equals(userEmail)) {
            return result.info(Info.REGISTER_EMAIL_NULL);
        }

        if("".equals(userPassword)) {
            return result.info(Info.REGISTER_PWD_NULL);
        }

        if(isEmailIllegal(userEmail)) {
            return result.info(Info.REGISTER_EMAIL_ILLEGAL);
        }

        if(isEmailDuplicate(userEmail)) {
            return result.info(Info.REGISTER_EMAIL_DUPLICATE);
        }

        if(isPasswordIllegal(userPassword)) {
            return result.info(Info.REGISTER_PWD_ILLEGAL);
        }

        User newUser = new User(
                generatedUserName(),
                userEmail,
                Encoder.encode(userPassword)
        );

        if(userService.insert(newUser) != 0) {
            return result.info(Info.REGISTER_SUCCESS);
        } else {
            return result.info(Info.REGISTER_FAIL);
        }
    }

    private boolean isEmailIllegal(String email) {
        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        return !emailRegex.matcher(email).matches();
    }

    public boolean isEmailDuplicate(String email) {
        return userService.selectOneByEmail(email) != null;
    }

    private boolean isPasswordIllegal(String pwd) {
        Pattern pwdRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$");
        return !pwdRegex.matcher(pwd).matches();
    }

    private String generatedUserName() {
        long time = System.currentTimeMillis();
        long curr = 0x17F7B000000L;
        return "User_" + Long.toHexString(time - curr);
    }
}