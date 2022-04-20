package com.queslime.controller;

import com.queslime.service.ProblemService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class ProblemController {
    @Resource
    private ProblemService problemService;


}
