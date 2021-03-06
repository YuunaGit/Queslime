package com.queslime.controller;

import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.entity.UserLikeSolution;
import com.queslime.enums.Info;
import com.queslime.service.ProblemService;
import com.queslime.service.SolutionService;
import com.queslime.service.UserLikeSolutionService;
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
public class LikeController {
    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    @Resource
    private UserLikeSolutionService userLikeSolutionService;

    @Resource
    private ProblemService problemService;

    @RequestMapping(value = "like")
    public Result userLikeOneSolution(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                      @RequestParam(value = "sid", defaultValue = "")String sidString,
                                      @RequestParam(value = "like", defaultValue = "")String likeString) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(sidString)) {
            return result.info(Info.CONTENT_NULL);
        }

        if("".equals(likeString)) {
            return result.info(Info.LIKE_PARAM_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if (uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if (user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        int sid = solutionService.stringToSid(sidString);
        if (sid == 0) {
            return result.info(Info.SID_ILLEGAL);
        }

        Solution solution = solutionService.selectOneBySid(sid);
        if (solution == null) {
            return result.info(Info.SID_NOT_EXISTS);
        }

        int like;
        try {
            like = Integer.parseInt(likeString);
        } catch (NumberFormatException e) {
            return result.info(Info.LIKE_PARAM_NULL);
        }

        if(like != 1 && like != 0) {
            return result.info(Info.LIKE_PARAM_ILLEGAL);
        }

        UserLikeSolution uls = userLikeSolutionService.selectOne(uid, sid);
        if(uls == null && like == 0) {
            return result.info(Info.CANCEL_LIKE);
        }

        if(uls != null && like == 1) {
            return result.info(Info.ALREADY_LIKE);
        }

        if(uls == null) {
            var newUls = new UserLikeSolution(uid, sid);

            if(userLikeSolutionService.insert(newUls) == 0) {
                return result.info(Info.FAIL);
            }
        }

        if(uls != null) {
            if(userLikeSolutionService.delete(uid, sid) == 0) {
                return result.info(Info.FAIL);
            }
        }

        Integer likeCount = userLikeSolutionService.selectLikeCountBySid(sid);

        solution.setLikeCount(likeCount);

        solutionService.update(solution);

        return result.info(Info.SUCCESS);
    }

    @RequestMapping(value = "/most/likes")
    public Result getMostLikeCountSolutions(@RequestParam(value = "uid", defaultValue = "")String uidString) {
        Result result = new Result();

        if("".equals(uidString)) {
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

        var solutionList = (ArrayList<Solution>) solutionService.selectMaxLikeCountSolutionList();

        var data = new ArrayList<HashMap<String, Object>>(3);

        for(int i = 0; i < 5; i++) {
            Solution s = solutionList.get(i);
            if(s.getLikeCount() >= 1) {
                data.add(solutionService.solutionSimpleWrapper(user, s));
            }
        }

        return result.info(Info.SUCCESS, data);
    }
}
