package com.queslime.controller;

import com.queslime.entity.Problem;
import com.queslime.entity.ProblemWithTags;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.ProblemService;
import com.queslime.service.ProblemWithTagsService;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class ProblemController {
    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemWithTagsService problemWithTagsService;

    @RequestMapping(value = "/post/problem")
    public Result postProblem(@RequestParam(value = "uid", defaultValue = "")String uidString,
                              @RequestParam(value = "content", defaultValue = "")String content,
                              @RequestParam(value = "tags", defaultValue = "")String[] tagsString) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(content)) {
            return result.info(Info.QUESTION_NULL);
        }

        if(tagsString.length == 0) {
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

        Problem newProblem = new Problem(
            user.getUid(),
            content
        );

        if(problemService.insert(newProblem) == 0) {
            return result.info(Info.FAIL);
        }

        int pid = problemService.selectCount();

        int tagsCount = tagsString.length;
        int[] tags = new int[tagsCount];
        for(int i = 0; i < tagsCount; i++) {
            try {
                tags[i] = Integer.parseInt(tagsString[i]);
            } catch (NumberFormatException e) {
                return result.info(Info.PROBLEM_TAG_ILLEGAL);
            }
        }

        for(int tid : tags) {
            ProblemWithTags pwt = new ProblemWithTags(pid, tid);
            if(problemWithTagsService.insert(pwt) == 0) {
                return result.info(Info.FAIL);
            }
        }

        return result.info(Info.SUCCESS);
    }
}
