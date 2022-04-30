package com.queslime.controller;

import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.ProblemService;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class ProblemController {
    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    @RequestMapping(value = "/post/problem")
    public Result postProblem(@RequestParam(value = "uid", defaultValue = "")String uidString,
                              @RequestParam(value = "content", defaultValue = "")String content,
                              @RequestParam(value = "tags", defaultValue = "")String[] tags) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(content)) {
            return result.info(Info.QUESTION_NULL);
        }

        if(tags.length == 0) {
            return result.info(Info.TAGS_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }




        return result;
    }
}
