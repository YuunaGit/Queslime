package com.queslime.service;

import com.queslime.mapper.SolutionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SolutionService {
    @Resource
    private SolutionMapper solutionMapper;


}
