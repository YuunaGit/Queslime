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
    ACCOUNT_NULL(111, "账户名不能为空"),
    PWD_NULL(112, "密码不能为空"),
    USER_NAME_NULL(113, "用户名不能为空"),
    // Public: Success or Fail
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    // Register
    REGISTER_EMAIL_ILLEGAL(211, "非法邮箱格式"),
    REGISTER_EMAIL_DUPLICATE(212, "邮箱已被使用"),
    REGISTER_ACCOUNT_DUPLICATE(213, "账户名已被使用"),
    REGISTER_EMAIL_TOO_LONG(214, "邮箱长度过长"),
    REGISTER_ACCOUNT_TOO_LONG(215, "账户名长度过长"),
    REGISTER_PWD_ILLEGAL(216,"密码长度应在6到20位之间，且至少包含一个字母和一个数字"),
    // Activate
    ACTIVATE_ALREADY_ACTIVATED(220, "账号已经激活"),
    ACTIVATE_EMAIL_SEND(221, "邮件成功发送");

    private final int code;
    private final String msg;

    Info(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
