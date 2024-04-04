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
    UNAUTHORIZED(401, "用户为经过认证"),
    /**
     * token已过期,需要重新登录
     */
    TOKEN_EXPIRED(402, "token已过期"),
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
