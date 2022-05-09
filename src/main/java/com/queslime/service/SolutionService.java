package com.queslime.service;

import com.queslime.entity.Solution;
import com.queslime.mapper.SolutionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SolutionService {
    @Resource
    private SolutionMapper solutionMapper;

    // Create
    public int insert(Solution solution) {
        return solutionMapper.insert(solution);
    }

    // Update
    public int update(Solution solution) {
        return solutionMapper.updateById(solution);
    }

    // Retrieve
    public Solution selectOneBySid(int sid) {
        return solutionMapper.selectById(sid);
    }
}
