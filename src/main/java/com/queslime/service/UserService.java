package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.User;
import com.queslime.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    // Create
    public int insert(User user) {
        return userMapper.insert(user);
    }

    // Retrieve
    public User selectOneByUid(int uid) {
        return userMapper.selectById(uid);
    }

    public User selectOneByEmail(String email) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("user_email", email)
        );
    }

    // Update
    public int update(User user) {
        return userMapper.updateById(user);
    }
}
