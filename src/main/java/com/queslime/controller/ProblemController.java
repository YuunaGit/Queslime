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
import java.util.*;

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
                              @RequestParam(value = "tags", defaultValue = "") String[] tagsIdString) {
        Result result = new Result();

        if ("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if ("".equals(content)) {
            return result.info(Info.PROBLEM_NULL);
        }

        if (content.length() > 5000) {
            return result.info(Info.PROBLEM_CONTENT_TOO_LONG);
        }

        if (tagsIdString.length == 0) {
            return result.info(Info.TAGS_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if (uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if (user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        Problem newProblem = new Problem(
                user.getUid(),
                content);

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

        if (problemService.insert(newProblem) == 0) {
            return result.info(Info.FAIL);
        }

        for (int tid : tagsId) {
            ProblemWithTags pwt = new ProblemWithTags(pid, tid);
            if (problemWithTagsService.insert(pwt) == 0) {
                return result.info(Info.FAIL);
            }
        }

        return result.info(Info.SUCCESS);
    }

    @RequestMapping(value = "/add/tag/to")
    public Result addTagsToProblem(@RequestParam(value = "pid", defaultValue = "")String pidString,
                                   @RequestParam(value = "tags", defaultValue = "") String[] tagsIdString) {
        Result result = new Result();
                                    
        if ("".equals(pidString)) {
            return result.info(Info.PID_NULL);
        }

        if (tagsIdString.length == 0) {
            return result.info(Info.TAGS_NULL);
        }

        int pid = problemService.stringToPid(pidString);
        if (pid == 0) {
            return result.info(Info.PID_ILLEGAL);
        }

        Problem problem = problemService.selectOneByPid(pid);
        if (problem == null) {
            return result.info(Info.PID_NOT_EXISTS);
        }

        int[] tagsId;
        int tagsCount = tagsIdString.length;
        tagsId = new int[tagsCount];
        try {
            for (int i = 0; i < tagsCount; i++) {
                tagsId[i] = Integer.parseInt(tagsIdString[i]);
            }
        } catch (NumberFormatException e) {
            return result.info(Info.PROBLEM_TAG_ILLEGAL);
        }
        
        for (int tid : tagsId) {
            ProblemWithTags pwt = new ProblemWithTags(pid, tid);
            if (problemWithTagsService.insert(pwt) == 0) {
                return result.info(Info.FAIL);
            }
        }

        return result.info(Info.SUCCESS);
    }

    @RequestMapping(value = "/get/problems")
    public Result getProblemsBy(@RequestParam(value = "search", defaultValue = "")String search,
                                @RequestParam(value = "tags", defaultValue = "")String[] tagsIdString,
                                @RequestParam(value = "order", defaultValue = "")String orderString) {
        Result result = new Result();

        // 0: new, 1: hot
        int order = 0;
        if("".equals(orderString)) {
            try {
                order = Integer.parseInt(orderString);
            } catch (NumberFormatException e) {
                return result.info(Info.PROBLEM_TAG_ILLEGAL);
            }
        }

        List<Problem> problems;

        boolean hasSearch = !"".equals(search);
        boolean hasTags = tagsIdString.length != 0;

        if(hasSearch) {
            if(search.length() > 200) {
                return result.info(Info.PROBLEM_SEARCH_TOO_LONG);
            }
        }

        int[] tagsId;
        if(hasTags) {
            int tagsCount = tagsIdString.length;
            tagsId = new int[tagsCount];
            try {
                for (int i = 0; i < tagsCount; i++) {
                    tagsId[i] = Integer.parseInt(tagsIdString[i]);
                }
            } catch (NumberFormatException e) {
                return result.info(Info.PROBLEM_TAG_ILLEGAL);
            }
        }

        if (hasSearch) {
            if (hasTags) {
                problems = problemService.selectListBySearch(search);
                problems.retainAll(problemService.selectListByTags(tagsId));
            } else {
                problems = problemService.selectListBySearch(search);
            }
        } else {
            if (hasTags) {
                problems = problemService.selectListByTags(tagsId);
            } else {
                problems = problemService.selectListAll();
            }
        }

        switch (order){
            case 0 -> problems.sort(Comparator.comparingInt(Problem::getPid));
            case 1 -> problems.sort();
        }


        return result;
    }

    @RequestMapping(value = "/get/problem")
    public Result getProblem(@RequestParam(value = "pid", defaultValue = "")String pidString) {
        Result result = new Result();



        return result;
    }
    
}
