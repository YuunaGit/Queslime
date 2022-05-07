package com.queslime.controller;

import com.queslime.service.SolutionService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class SolutionController {
    @Resource
    private SolutionService solutionService;

    @RequestMapping(value = "/post/solution")
    public Result postSolution(@RequestParam(value = "pid", defaultValue = "")String pidString,
                               @RequestParam(value = "uid", defaultValue = "")String uidString,
                               @RequestParam(value = "content", defaultValue = "")String solutionContent) {
        Result result = new Result();
        

        return result;
    }
}
