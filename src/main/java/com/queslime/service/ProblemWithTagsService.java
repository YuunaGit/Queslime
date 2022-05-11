package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Problem;
import com.queslime.entity.ProblemWithTags;
import com.queslime.mapper.ProblemWithTagsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class ProblemWithTagsService {
    @Resource
    private ProblemWithTagsMapper problemWithTagsMapper;

    // Create
    public int insert(ProblemWithTags problemWithTags) {
        return problemWithTagsMapper.insert(problemWithTags);
    }

    public int[] selectProblemsIdByTags(int[] tagsId) {
        List<ProblemWithTags> pwtList = problemWithTagsMapper.selectList(
            new QueryWrapper<ProblemWithTags>().in("tid", tagsId)
        );
        
        int[] problemsIdList = new int[pwtList.size()];
        int index = 0;
        for (ProblemWithTags pwt : pwtList) {
            problemsIdList[index++] = pwt.getPid();
        }
        return problemsIdList;
    }
}
