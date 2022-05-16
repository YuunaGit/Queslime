package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.UserLikeSolution;
import com.queslime.mapper.UserLikeSolutionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserLikeSolutionService {
    @Resource
    private UserLikeSolutionMapper userLikeSolutionMapper;

    // Create
    public int insert(UserLikeSolution userLikeSolution) {
        return userLikeSolutionMapper.insert(userLikeSolution);
    }

    // Delete
    public int delete(int uid, int sid) {
        return userLikeSolutionMapper.delete(
                new QueryWrapper<UserLikeSolution>()
                        .eq("uid", uid)
                        .eq("sid", sid)
        );
    }

    // Retrieve
    public UserLikeSolution selectOne(int uid, int sid) {
        return userLikeSolutionMapper.selectOne(
                new QueryWrapper<UserLikeSolution>()
                        .eq("uid", uid)
                        .eq("sid", sid)
        );
    }

    public Integer selectLikeCountBySid(int sid) {
        return userLikeSolutionMapper.selectCount(
                new QueryWrapper<UserLikeSolution>().eq("sid", sid)
        ).intValue();
    }
}
