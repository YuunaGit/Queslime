package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserInfoController {
    @Resource
    private UserService userService;

    @RequestMapping("/update/user/name")
    public Result updateUserProfile(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                    @RequestParam(value = "newName", defaultValue = "")String newUserame) {
        Result result = new Result();

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        if("".equals(newUserame)) {
            return result.info(Info.USER_NAME_NULL);
        }

        return result;
    }
}
