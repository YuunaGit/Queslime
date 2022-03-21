package com.queslime.controller;

import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class ProfileController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/update/user/{uid}/name")
    public Result updateUserName(@PathVariable(value = "uid")String uidString,
                                 @RequestParam(value = "username", defaultValue = "")String userName) {
        Result result = new Result();



        return result;
    }

    @RequestMapping(value = "/update/user/{uid}/email")
    public Result updateEmail(@PathVariable(value = "uid")String uidString,
                              @RequestParam(value = "email", defaultValue = "")String userEmail) {
        Result result = new Result();



        return result;
    }
}
