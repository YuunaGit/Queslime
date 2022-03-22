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
    UID_NULL(110, "UID不能为空"),
    EMAIL_NULL(111, "邮箱不能为空"),
    PWD_NULL(112, "密码不能为空"),
    USER_NAME_NULL(113, "用户名不能为空"),
    // Public: Success or Fail
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    // Register
    REGISTER_EMAIL_ILLEGAL(210, "非法邮箱格式"),
    REGISTER_EMAIL_DUPLICATE(211, "邮箱已被使用"),
    REGISTER_EMAIL_TOO_LONG(212, "邮箱长度过长"),
    REGISTER_PWD_ILLEGAL(213,"密码长度应在6到20位之间，且至少包含一个字母和一个数字"),
    // Activate
    ACTIVATE_ALREADY_ACTIVATED(220, "账号已经激活过了"),
    ACTIVATE_EMAIL_SEND(221, "邮件成功发送"),
    // Update
    UPDATE_USER_NAME_DUPLICATE(231, "用户名重复");

    private final int code;
    private final String msg;

    Info(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
