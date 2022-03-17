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

    private final Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private final Pattern pwdRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$");

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

    // Service
    public int stringToUid(String uidString) {
        int uid;
        try {
            uid = Integer.parseInt(uidString);
        } catch (NumberFormatException e) {
            return 0;
        }
        if(uid < 1) {
            return 0;
        }
        return uid;
    }

    public boolean isEmailIllegal(String email) {
        return !emailRegex.matcher(email).matches();
    }

    public boolean isEmailDuplicate(String email) {
        return selectOneByEmail(email) != null;
    }

    public boolean isPasswordIllegal(String pwd) {
        return !pwdRegex.matcher(pwd).matches();
    }

    public String generatedUserName() {
        long time = System.currentTimeMillis();
        long curr = 0x17F7B000000L;
        return "User_" + Long.toHexString(time - curr);
    }
}
