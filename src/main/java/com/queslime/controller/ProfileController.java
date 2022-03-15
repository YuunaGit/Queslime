package com.queslime.controller;

import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    private UserService userService;

    @RequestMapping("/update/user/name")
    public Result updateUserName() {
        Result result = new Result();


        return result;
    }
}
