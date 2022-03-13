package com.queslime.controller;

import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserInfoController {
    @Resource
    private UserService userService;

    public Result updateUserProfile() {
        Result result = new Result();
        // TODO: 2022/3/13  

        return result;
    }
}
