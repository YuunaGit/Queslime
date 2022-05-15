package com.queslime.controller;

import com.queslime.entity.Problem;
import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.SolutionService;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class LikeController {
    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    public Result userLikeSolution(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                   @RequestParam(value = "sid", defaultValue = "")String sidString,
                                   @RequestParam(value = "like", defaultValue = "")String likeString) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(sidString)) {
            return result.info(Info.SOLUTION_NULL);
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

        if(like != 1 && like != -1) {
            return result.info(Info.LIKE_PARAM_NULL);
        }

        


        return result;
    }
}
