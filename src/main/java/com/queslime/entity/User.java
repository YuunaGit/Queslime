package com.queslime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.queslime.enums.entityEnum.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(schema = "queslime", value = "e_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String userName;
    private String userEmail;
    private String userPassword;
    private UserStatus userState;
    private Timestamp createdAt;

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userState = UserStatus.NOT_ACTIVATED;
    }
}
