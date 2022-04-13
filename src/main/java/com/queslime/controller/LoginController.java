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
public class LoginController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/login/email")
    public Result loginUserByEmail(@RequestParam(value = "email", defaultValue = "")String userEmail,
                                   @RequestParam(value = "password", defaultValue = "")String userPassword) {
        Result result = new Result();

        if("".equals(userEmail)) {
            return result.info(Info.EMAIL_NULL);
        }

        if("".equals(userPassword)) {
            return result.info(Info.PWD_NULL);
        }

        User user = userService.selectOneByEmail(userEmail);

        return loginUser(user, userPassword);
    }

    @RequestMapping(value = "/login/name")
    public Result loginUserByUserName(@RequestParam(value = "username", defaultValue = "")String userName,
                                      @RequestParam(value = "password", defaultValue = "")String userPassword) {
        Result result = new Result();

        if("".equals(userName)) {
            return result.info(Info.USER_NAME_NULL);
        }

        if("".equals(userPassword)) {
            return result.info(Info.PWD_NULL);
        }

        User user = userService.selectOneByUserName(userName);

        return loginUser(user, userPassword);
    }

    private Result loginUser(User user, String userPassword) {
        Result result = new Result();

        if(user == null) {
            return result.info(Info.FAIL);
        }

        if(Encoder.notMatch(userPassword, user.getUserPassword())) {
            return result.info(Info.FAIL);
        }

        var data = userService.userWrapper(user);

        return result.info(Info.SUCCESS, data);
    }
}
