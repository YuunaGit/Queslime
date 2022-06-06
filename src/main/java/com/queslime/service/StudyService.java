package com.queslime.service;

import java.util.List;
import java.util.TreeSet;

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

    // Update
    public int update(Study study) {
        return studyMapper.updateById(study);
    }

    // Retrieve
    public List<Study> selectListByUid(long uid) {
        return studyMapper.selectList(
                new QueryWrapper<Study>().eq("uid", uid)
        );
    }

    public Study selectOneByUidPid(long uid, long pid) {
        return studyMapper.selectOne(new QueryWrapper<Study>().eq("uid", uid).eq("pid", pid));
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

    public long selectCountDiffByUid(long uid, int pass, int diff) {
        return studyMapper.selectCount(
                new QueryWrapper<Study>().eq("uid", uid).eq("pass", pass).eq("difficulty", diff)
        );
    }

    public int[] selectPassAndNotPassCountByUidDiff(long uid, int diff) {
        var res = new int[2];
        var studyPassList = studyMapper.selectList(
                new QueryWrapper<Study>().eq("uid", uid).eq("pass", 1).eq("difficulty", diff)
        );
        var tsPass = new TreeSet<Integer>();
        for(Study s : studyPassList) {
            tsPass.add(s.getPid());
        }
        res[0] = tsPass.size();

        var studyAllList = studyMapper.selectList(
                new QueryWrapper<Study>().eq("uid", uid).eq("difficulty", diff)
        );
        var tsAll = new TreeSet<Integer>();
        for(Study s : studyAllList) {
            tsAll.add(s.getPid());
        }

        tsAll.removeAll(tsPass);

        res[1] = tsAll.size();

        return res;
    }


}
