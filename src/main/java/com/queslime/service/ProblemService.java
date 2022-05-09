package com.queslime.service;

import com.queslime.entity.Problem;
import com.queslime.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProblemService {
    @Resource
    private ProblemMapper problemMapper;

    // Create
    public int insert(Problem problem) {
        return problemMapper.insert(problem);
    }

    // Update
    public int update(Problem problem) {
        return problemMapper.insert(problem);
    }

    // Retrieve
    public Problem selectOneByUid(int pid) {
        return problemMapper.selectById(pid);
    }

    public int selectCount() {
        return Math.toIntExact(problemMapper.selectCount(null));
    }

    // Wrapper

    // Service
}
