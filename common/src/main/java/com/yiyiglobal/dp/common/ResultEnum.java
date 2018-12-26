package com.yiyiglobal.dp.common;

/**
 * Created by wangzukun on 2017/6/14.
 */
public enum ResultEnum {
    SUCCESS(1, "请求OK"),

    UNKONOWN_ERROR(-1, "未知错误"),

    SYSTEM_ERROR(10001, "系统错误"),
    SERVICE_UNAVAILABLE(10002, "服务暂停"),
    API_NOT_FOUND(10003, "请求的接口不存在"),
    IP_LIMIT(10004, "IP限制不能请求该资源"),
    IP_REQUEST_OVER_LIMIT(10005, "IP请求频次超过上限"),
    USER_REQUEST_OVER_LIMIT(10006, "用户请求频次超过上限"),
    MISS_REQUIRED_PARAM(10007, "缺失必选参数"),
    PARAM_INVALID(10008, "参数值非法"),

    ACCESSTOKEN_INVALID(10102, "授权码不合法或已过期"),

    ID_NOT_EXIST(20001, "id对应资源不存在或已删除"),
    USER_LOCK(20102, "用户因为违规操作，已被锁定"),
    MOBILE_NULL(20111, "手机号不可以为空"),
    MOBILE_INVALID(20112, "手机号格式不正确"),
    VERIFYCODE_NULL(20116, "验证码不可以为空"),
    VERIFYCODE_INVALID(20117, "验证码格式不正确"),
    VERIFYCODE_NOT_CORRECT(20118, "验证码不正确"),
    VERIFYCODE_TOO_FREQUENCY(20119, "验证码发送过于频繁"),
    VERIFYCODE_EXPIRED(20120, "验证码已经过期"),
    VERIFYCODE_SEND_FAIL(20121, "验证码发送失败"),
    VERIFYCODE_CHECK_TOO_MUCH(20122, "验证码输入次数过多（超5次），已失效"),
    NICKNAME_INVALID(20137, "昵称格式不正确，长度需在4-30之间，不能再用默认昵称"),
    NICKNAME_EXIST(20138, "昵称已存在"),
    SEX_INVALID(20142, "性别格式不正确"),
    BIRTHDAY_INVALID(20147, "生日格式不正确"),

    ALREADY_SIGNIN(30001, "今日已经签到");


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

