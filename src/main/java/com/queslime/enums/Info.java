package com.queslime.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Info {
    // Public: Uid
    UID_ILLEGAL(101, "用户UID非法"),
    UID_NOT_EXISTS(102, "用户不存在"),
    // Public: Not null
    EMAIL_NULL(103, "邮箱不能为空"),
    PWD_NULL(104, "密码不能为空"),
    // Register
    REGISTER_SUCCESS(110, "注册账号成功"),
    REGISTER_FAIL(111, "注册账号失败"),
    REGISTER_EMAIL_ILLEGAL(112, "非法邮箱格式"),
    REGISTER_EMAIL_DUPLICATE(113, "邮箱已被使用"),
    REGISTER_PWD_ILLEGAL(114,"密码长度应在6到20位之间，且至少包含一个字母和一个数字"),
    // Activate
    ACTIVATE_SUCCESS(120, "账号激活成功"),
    ACTIVATE_FAIL(121, "错误代码"),
    ACTIVATE_ALREADY_ACTIVATED(122, "账号已经激活"),
    ACTIVATE_EMAIL_SEND(123, "邮件成功发送"),
    // Login
    LOGIN_SUCCESS(130, "登录成功"),
    LOGIN_EMAIL_OR_PWD_WRONG(131, "邮箱或密码错误");


    private final int code;
    private final String msg;

    Info(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
