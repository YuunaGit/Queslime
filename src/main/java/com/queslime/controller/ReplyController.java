package com.queslime.controller;

import com.queslime.service.ReplyService;
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

    @RequestMapping(value = "/post/reply")
    public Result postReply(@RequestParam(value = "sid", defaultValue = "")String sidString,
                            @RequestParam(value = "uid", defaultValue = "")String postUidString,
                            @RequestParam(value = "to", defaultValue = "")String toUidString,
                            @RequestParam(value = "content", defaultValue = "")String content) {
        Result result = new Result();




        return result;
    }

}
