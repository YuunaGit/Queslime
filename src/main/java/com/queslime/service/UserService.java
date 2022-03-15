package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.User;
import com.queslime.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Pattern;

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

    // Update
    public int update(User user) {
        return userMapper.updateById(user);
    }

    // Service
    public boolean isEmailIllegal(String email) {
        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        return !emailRegex.matcher(email).matches();
    }

    public boolean isEmailDuplicate(String email) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("user_email", email)
        ) != null;
    }

    public boolean isPasswordIllegal(String pwd) {
        Pattern pwdRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$");
        return !pwdRegex.matcher(pwd).matches();
    }

    public String generatedUserName() {
        long time = System.currentTimeMillis();
        long curr = 0x17F7B000000L;
        return "User_" + Long.toHexString(time - curr);
    }
}
