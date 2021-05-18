package com.example.utils;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:46
 * @version: 1.0
 */
public enum ResultCode {
    // 常用httpRequest
    SUCCESS(200, "操作成功"),
    CLIENT_PARAM_ERROR(400, "客户端参数错误"),
    UN_AUTHORIZED(400, "Unauthorized"),
    FORBIDDEN(400, "Forbidden"),
    NOT_FOUND_PATH(400, "没有找到页面"),
    SERVER_ERROR(400, "服务器异常"),
    NULL_POINTER(400, "空指针异常"),
    NOT_FOUND_DATA(400, "查询数据为空"),
    FAILED(400, "操作失败"),
    DELETE_FAILED(400, "删除失败，与其他表有关联"),
    NOT_FOUND_RESOURCES(400, "没有找到资源"),
    IO_EXCEPTION(400, "读写异常"),
    LOGIN_SUCCESS(200, "登录成功"),
    LOGIN_FAILURE(400, "登录失败"),
    NO_AUTHORITY(400, "没有权限");

    // http状态码
    int code;

    // 操作提示信息
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
