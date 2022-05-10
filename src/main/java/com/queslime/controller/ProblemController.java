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
                              @RequestParam(value = "tags", defaultValue = "")String[] tagsIdString) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(content)) {
            return result.info(Info.PROBLEM_NULL);
        }

        if(content.length() > 5000) {
            return result.info(Info.PROBLEM_CONTENT_TOO_LONG);
        }

        if(tagsIdString.length == 0) {
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

        int pid = problemService.selectCount() + 1;

        int tagsCount = tagsIdString.length;
        int[] tagsId = new int[tagsCount];
        try {
            for (int i = 0; i < tagsCount; i++) {
                tagsId[i] = Integer.parseInt(tagsIdString[i]);
            }
        } catch (NumberFormatException e) {
            return result.info(Info.PROBLEM_TAG_ILLEGAL);
        }

        if(problemService.insert(newProblem) == 0) {
            return result.info(Info.FAIL);
        }
        
        for(int tid : tagsId) {
            ProblemWithTags pwt = new ProblemWithTags(pid, tid);
            if(problemWithTagsService.insert(pwt) == 0) {
                return result.info(Info.FAIL);
            }
        }

        return result.info(Info.SUCCESS);
    }

    @RequestMapping(value = "/get/problems")
    public Result getProblemsBy(@RequestParam(value = "search", defaultValue = "")String search,
                                @RequestParam(value = "tags", defaultValue = "")String[] tagsIdString,
                                @RequestParam(value = "by", defaultValue = "")String by) {
        Result result = new Result();

        if(tagsIdString.length == 0) {

        }

        return result;
    }

    @RequestMapping(value = "/get/problem")
    public Result getProblem(@RequestParam(value = "pid", defaultValue = "")String pidString) {
        Result result = new Result();



        return result;
    }
    
}
