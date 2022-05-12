package com.queslime.controller;

import com.queslime.entity.Problem;
import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.ProblemService;
import com.queslime.service.SolutionService;
import com.queslime.service.UserService;
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
public class SolutionController {
    @Resource
    private SolutionService solutionService;

    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    @RequestMapping(value = "/post/solution")
    public Result postSolution(@RequestParam(value = "pid", defaultValue = "")String pidString,
                               @RequestParam(value = "uid", defaultValue = "")String uidString,
                               @RequestParam(value = "content", defaultValue = "") String solutionContent) {
        Result result = new Result();

        if ("".equals(pidString)) {
            return result.info(Info.PID_NULL);
        }

        if ("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if ("".equals(solutionContent)) {
            return result.info(Info.SOLUTION_NULL);
        }

        if (solutionContent.length() > 5000) {
            return result.info(Info.SOLUTION_CONTENT_TOO_LONG);
        }

        int uid = userService.stringToUid(uidString);
        if (uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if (user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        int pid = problemService.stringToPid(pidString);
        if (pid == 0) {
            return result.info(Info.PID_ILLEGAL);
        }

        Problem problem = problemService.selectOneByPid(pid);
        if (problem == null) {
            return result.info(Info.PID_NOT_EXISTS);
        }

        Solution newSolution = new Solution(
            problem.getPid(),
            user.getUid(),
            solutionContent
        );

        if (solutionService.insert(newSolution) == 0) {
            return result.info(Info.FAIL);
        }

        long sid = solutionService.selectCount();
        Solution solution = solutionService.selectOneBySid(sid);

        var data = solutionService.solutionWrapper(solution);

        return result.info(Info.SUCCESS, data);
    }

    @RequestMapping(value = "/get/solutions")
    public Result getSolutionsBy(@RequestParam(value = "uid", defaultValue = "") String uidString) {
        Result result = new Result();

        if ("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if (uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if (user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        var solutionList = solutionService.selectOneByUid(user.getUid());

        var data = new ArrayList<HashMap<String, Object>>();
        for(Solution s : solutionList) {
            data.add(solutionService.solutionWrapper(s));
        }

        return result.info(Info.SUCCESS, data);
    }
}
