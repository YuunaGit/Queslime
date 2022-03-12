package com.queslime.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Info {
    REGISTER_EMAIL_NULL(110, "邮箱不能为空"),
    REGISTER_PWD_NULL(111, "密码不能为空"),
    REGISTER_EMAIL_ILLEGAL(112, "非法邮箱格式"),
    REGISTER_EMAIL_DUPLICATE(113, "邮箱已被使用"),
    REGISTER_PWD_ILLEGAL(114,"密码长度应在6到20位之间，且至少包含一个字母和一个数字"),
    REGISTER_SUCCESS(115, "注册账号成功"),
    REGISTER_FAIL(116, "注册账号失败");

    private final int code;
    private final String msg;

    Info(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
