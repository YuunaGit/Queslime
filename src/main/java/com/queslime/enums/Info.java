package com.queslime.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Info {
    // Public: Uid, Pid, Tid, Sid
    UID_ILLEGAL(100, "用户UID非法"),
    UID_NOT_EXISTS(101, "用户不存在"),
    PID_ILLEGAL(102, "问题PID非法"),
    PID_NOT_EXISTS(103, "问题不存在"),
    SID_ILLEGAL(104, "题解PID非法"),
    SID_NOT_EXISTS(105, "题解不存在"),

    // Public: Not null
    UID_NULL(110, "UID不能为空"),
    EMAIL_NULL(111, "邮箱不能为空"),
    PWD_NULL(112, "密码不能为空"),
    USER_NAME_NULL(113, "用户名不能为空"),
    PROBLEM_NULL(114, "问题不能为空"),
    TAGS_NULL(115, "Tag不能为空"),
    PID_NULL(116, "PID不能为空"),
    CONTENT_NULL(117, "内容不能为空"),
    DIFFICULTY_NULL(118, "题目难度不能为空"),
    SID_NULL(119, "题解SID不能为空"),

    // Public: Duplicate
    EMAIL_DUPLICATE(120, "邮箱已被使用"),
    USER_NAME_DUPLICATE(121, "用户名重复"),

    // Public: Length limit
    EMAIL_TOO_LONG(210, "邮箱长度大于100"),
    USER_NAME_TOO_LONG(211, "UID长度大于30"),
    PROBLEM_CONTENT_TOO_LONG(212, "问题内容大于5000字"),
    SOLUTION_CONTENT_TOO_LONG(213, "题解内容大于5000字"),
    REPLY_CONTENT_TOO_LONG(214, "回复内容大于500字"),

    // Public: Success or Fail
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),

    // Register
    EMAIL_ILLEGAL(210, "非法邮箱格式"),
    PWD_ILLEGAL(211,"密码长度应在6到20位之间，且至少包含一个字母和一个数字"),

    // Activate
    ALREADY_ACTIVATED(220, "账号已经激活过了"),
    EMAIL_SEND(221, "邮件成功发送"),

    // Update
    PWD_WRONG(230, "旧密码不正确"),

    // Problem
    PROBLEM_TAG_ILLEGAL(240, "tag非法格式"),
    PROBLEM_SEARCH_TOO_LONG(241, "搜索内容太长，最多200字"),
    PROBLEM_ORDER_ILLEGAL(242, "搜索顺序格式非法"),
    PROBLEM_DIFFICULTY_ILLEGAL(243, "难度数值非法"),

    // Like
    LIKE_PARAM_NULL(250, "点赞参数不能为空"),
    LIKE_PARAM_ILLEGAL(251, "点赞参数只能为1或0"),
    CANCEL_LIKE(252, "你没有点赞过，取消什么？"),
    ALREADY_LIKE(253, "你已经赞过了，还赞什么？"),

    // Study
    STUDY_PARAM_ILLEGAL(260, "携带参数应为1或0"),

    // Update
    UPDATE_NO_PERMIT(270, "你没有权限更改此问题");

    private final int code;
    private final String msg;

    Info(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
