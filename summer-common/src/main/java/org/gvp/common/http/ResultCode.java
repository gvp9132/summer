package org.gvp.common.http;

/**
 * 返回结果状态码枚举类
 * https://blog.csdn.net/weixin_63149034/article/details/126365189
 * @date 2024/04/01
 * @author gvp9132
 * @version 2.0
 */
public enum ResultCode {
    SUCCESS(200, "请求成功"),
    LOGIN_SUCCESS(200, "登录成功"),
    /**
     * 请求成功,服务器一已经处理,但是没有响应数据
     */
    SUCCESS_NO_CONTENT(204, "请求成功,无内容"),
    /**
     * 请求失败
     */
    FAIL(400, "请求失败"),
    /**
     * 用户给身份未经认证
     */
    UNAUTHORIZED(401, "用户未经过认证"),

    LOGIN_FAILURE(402,"登录失败"),
    LOGIN_FAILURE_USER_NOT_FOUND(4021,"登录失败,用户名不存在"),
    LOGIN_FAILURE_PASSWORD_ERROR(4022,"登录失败,用户密码错误"),
    LOGIN_FAILURE_USER_DISABLED(4023,"登录失败,用户禁用"),
    LOGIN_FAILURE_USER_LOCKED(4024,"登录失败,用户锁定"),
    LOGIN_FAILURE_PASSWORD_EXPIRED(4025,"登录失败,用户密码过期"),
    LOGIN_FAILURE_USER_EXPIRED(4026,"登录失败,用户账户过期"),
    /**
     * 用户没有权限访问
     */
    FORBIDDEN(403, "用户没有权限访问"),
    /**
     * 未找到资源
     */
    NOT_FOUND(404, "未找到"),
    /**
     * 不支持的请求方法
     */
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),
    /**
     * token已过期,需要重新登录
     */
    TOKEN_EXPIRED(424, "token已过期超时"),

    // 499 需要令牌
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");
    private final int code;
    private final String message;
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
