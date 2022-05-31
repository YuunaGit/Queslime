package com.queslime.controller;

import com.queslime.entity.Problem;
import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.enums.entityEnum.UserState;
import com.queslime.service.UserService;
import com.queslime.utils.Encoder;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin
@RestController
public class ProfileController {
    @Resource
    private UserService userService;

    @RequestMapping("/update/user/name")
    public Result updateUserName(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                 @RequestParam(value = "username", defaultValue = "")String newUserName) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(newUserName)) {
            return result.info(Info.USER_NAME_NULL);
        }

        if(newUserName.length() >= 30) {
            return result.info(Info.USER_NAME_TOO_LONG);
        }

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        if(userService.isUserNameDuplicate(newUserName)) {
            return result.info(Info.USER_NAME_DUPLICATE);
        }

        user.setUserName(newUserName);
        if(userService.update(user) == 0) {
            return result.info(Info.FAIL);
        }

        return result.info(Info.SUCCESS);
    }

    @RequestMapping("/update/user/email")
    public Result updateUserEmail(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                  @RequestParam(value = "email", defaultValue = "")String newEmail) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(newEmail)) {
            return result.info(Info.EMAIL_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        if(userService.isEmailDuplicate(newEmail)) {
            return result.info(Info.EMAIL_DUPLICATE);
        }

        user.setUserEmail(newEmail);
        user.setUserState(UserState.NOT_ACTIVATED);
        if(userService.update(user) == 0) {
            return result.info(Info.FAIL);
        }

        return result.info(Info.SUCCESS);
    }

    @RequestMapping("/update/user/password")
    public Result updateUserPwd(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                @RequestParam(value = "old_password", defaultValue = "")String oldPassword,
                                @RequestParam(value = "new_password", defaultValue = "")String newPassword) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(newPassword)) {
            return result.info(Info.PWD_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        if(Encoder.notMatch(oldPassword, user.getUserPassword())) {
            return result.info(Info.PWD_WRONG);
        }

        user.setUserPassword(Encoder.encode(newPassword));
        if(userService.update(user) == 0) {
            return result.info(Info.FAIL);
        }

        return result.info(Info.SUCCESS);
    }

}
