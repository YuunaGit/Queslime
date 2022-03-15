package com.queslime.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Info {
    // Public: Uid
    UID_ILLEGAL(100, "用户UID非法"),
    UID_NOT_EXISTS(101, "用户不存在"),
    // Public: Not null
    EMAIL_NULL(110, "邮箱不能为空"),
    PWD_NULL(111, "密码不能为空"),
    USER_NAME_NULL(112, "用户名不能为空"),
    // Register
    REGISTER_SUCCESS(210, "注册账号成功"),
    REGISTER_FAIL(211, "注册账号失败"),
    REGISTER_EMAIL_ILLEGAL(212, "非法邮箱格式"),
    REGISTER_EMAIL_DUPLICATE(213, "邮箱已被使用"),
    REGISTER_PWD_ILLEGAL(214,"密码长度应在6到20位之间，且至少包含一个字母和一个数字"),
    // Activate
    ACTIVATE_SUCCESS(220, "账号激活成功"),
    ACTIVATE_FAIL(221, "错误代码"),
    ACTIVATE_ALREADY_ACTIVATED(222, "账号已经激活"),
    ACTIVATE_EMAIL_SEND(223, "邮件成功发送"),
    // Login
    LOGIN_SUCCESS(230, "登录成功"),
    LOGIN_EMAIL_OR_PWD_WRONG(231, "邮箱或密码错误");

    private final int code;
    private final String msg;

    Info(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
