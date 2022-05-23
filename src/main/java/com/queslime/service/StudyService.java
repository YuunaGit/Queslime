package com.queslime.service;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Study;
import com.queslime.mapper.StudyMapper;

import org.springframework.stereotype.Service;

@Service
public class StudyService {
    @Resource
    private StudyMapper studyMapper;

    // Create
    public int insert(Study study) {
        return studyMapper.insert(study);
    }

    
    // Retrieve
    public List<Study> selectPassListByUid(long uid) {
        return studyMapper.selectList(
                new QueryWrapper<Study>().eq("uid", uid).eq("pass", 1)
        );
    }
    
    public List<Study> selectNotPassListByUid(long uid) {
        return studyMapper.selectList(
                new QueryWrapper<Study>().eq("uid", uid).eq("pass", 0));
    }
    
}
