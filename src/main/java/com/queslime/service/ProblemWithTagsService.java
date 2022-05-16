package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.ProblemWithTags;
import com.queslime.mapper.ProblemWithTagsMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ArrayList<Integer> selectProblemsIdByTags(ArrayList<Integer> tagsIdList) {
        List<ProblemWithTags> pwtList = problemWithTagsMapper.selectList(
            new QueryWrapper<ProblemWithTags>().in("tid", tagsIdList)
        );
        
        var problemsIdList = new ArrayList<Integer>();
        for (ProblemWithTags pwt : pwtList) {
            problemsIdList.add(pwt.getPid());
        }
        return problemsIdList;
    }

    public ArrayList<Integer> selectTagsByPid(Integer pid) {
        List<ProblemWithTags> pwtList = problemWithTagsMapper.selectList(
            new QueryWrapper<ProblemWithTags>().eq("pid", pid)
        );

        var tidList = new ArrayList<Integer>();
        for (ProblemWithTags pwt : pwtList) {
            tidList.add(pwt.getTid());
        }
        return tidList;
    }
}
