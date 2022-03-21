package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.User;
import com.queslime.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    private final static Pattern emailRegex = Pattern.compile(
            "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"
    );

    private final static Pattern pwdRegex = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$"
    );

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

    public User selectOneByAccount(String account) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("user_account", account)
        );
    }

    // Update
    public int update(User user) {
        return userMapper.updateById(user);
    }

    // Wrapper
    public HashMap<String, String> userWrapper(User user) {
        HashMap<String, String> data = new HashMap<>();
        data.put("uid", user.getUid().toString());
        data.put("user_name", user.getUserName());
        data.put("user_email", user.getUserEmail());
        data.put("user_account", user.getUserAccount());
        return data;
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

    public boolean isAccountDuplicate(String account) {
        return selectOneByAccount(account) != null;
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
