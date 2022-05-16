package com.queslime.controller;

import com.queslime.entity.Reply;
import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.ReplyService;
import com.queslime.service.SolutionService;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class ReplyController {
    @Resource
    private ReplyService replyService;

    @Resource
    private SolutionService solutionService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/post/reply")
    public Result postReply(@RequestParam(value = "sid", defaultValue = "")String sidString,
                            @RequestParam(value = "uid", defaultValue = "")String postUidString,
                            @RequestParam(value = "to", defaultValue = "")String toUidString,
                            @RequestParam(value = "content", defaultValue = "")String content) {
        Result result = new Result();

        if("".equals(sidString)) {
            return result.info(Info.SID_NULL);
        }

        if("".equals(postUidString)) {
            return result.info(Info.UID_NULL);
        }

        if("".equals(content)) {
            return result.info(Info.CONTENT_NULL);
        }

        if(content.length() >= 500) {
            return result.info(Info.REPLY_CONTENT_TOO_LONG);
        }

        int sid = solutionService.stringToSid(sidString);
        if (sid == 0) {
            return result.info(Info.SID_ILLEGAL);
        }

        Solution solution = solutionService.selectOneBySid(sid);
        if (solution == null) {
            return result.info(Info.SID_NOT_EXISTS);
        }

        int postUid = userService.stringToUid(postUidString);
        if (postUid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User postUser = userService.selectOneByUid(postUid);
        if (postUser == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        int toUid = 0;
        if(!"".equals(toUidString)) {
            toUid = userService.stringToUid(toUidString);
            if (toUid == 0) {
                return result.info(Info.UID_ILLEGAL);
            }

            User user = userService.selectOneByUid(toUid);
            if (user == null) {
                return result.info(Info.UID_NOT_EXISTS);
            }
        }

        var newReply = new Reply(sid, postUid, toUid, content);

        if(replyService.insert(newReply) == 0) {
            return result.info(Info.FAIL);
        }

        return result.info(Info.SUCCESS);
    }

}
