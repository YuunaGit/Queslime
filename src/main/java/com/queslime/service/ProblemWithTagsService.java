package com.queslime.service;

import com.queslime.entity.ProblemWithTags;
import com.queslime.mapper.ProblemWithTagsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProblemWithTagsService {
    @Resource
    private ProblemWithTagsMapper problemWithTagsMapper;

    // Create
    public int insert(ProblemWithTags problemWithTags) {
        return problemWithTagsMapper.insert(problemWithTags);
    }
}
