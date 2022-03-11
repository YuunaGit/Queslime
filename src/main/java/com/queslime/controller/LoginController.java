package com.queslime.controller;

import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

public class LoginController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public Result loginUserAsEmail(@RequestParam(value = "email")String userEmail,
                                   @RequestParam(value = "password")String userPassword) {
        Result result = new Result(null);
        return result;
    }

    @RequestMapping("/login")
    public Result loginUserAsUsername(@RequestParam(value = "email")String userEmail,
                                      @RequestParam(value = "password")String userPassword) {
        Result result = new Result(null);
        return result;
    }
}
