package com.queslime.controller;

import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MailController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/activate")
    public Result accountActivate() {
        Result result = new Result(null);

        return result;
    }
}
