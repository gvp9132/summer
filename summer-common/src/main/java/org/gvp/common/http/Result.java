package org.gvp.common.http;
import lombok.Data;

import java.time.Instant;

/**
 * 返回结果类对象
 * @param <T> 封装的数据类型
 * @author gvp9132
 * @version 2.0
 */
@Data
//@Builder
public class Result<T> {
    /** 全局响应状态码 */
    private int code;
    /** 全局响应提示信息 */
    private String message;
    /** 全局响应数据成功状态 */
    private boolean success;
    /** 全局响应时间戳 */
    private Instant timestamp;
    /** 全局响应状真实数据 */
    private T data;

    private Result() {
    }
    private Result(int code, String message, boolean success, Instant timestamp, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.timestamp = timestamp;
        this.data = data;
    }
    /** 构建一个总是成功的响应,但是如果数据为空,则success为false */
    public static <T> Result<T> ok(T data){
        return new Result<T>(200, "请求成功", data != null, Instant.now(), data) ;
    }
    /** 构建一个总是失败的响应 */
    public static <T> Result<T> fail(T data){
        return new Result<T>(400, "请求失败", false, Instant.now(), data) ;
    }
    /** 使用http状态码构建一个响应 */
    public static <T> Result<T> resultCoder(ResultCode code){
        return new Result<T>(code.code(), code.message(), false, Instant.now(), null) ;
    }
    /** 构建一个响应创建的对象 */
    public static <T> ResultBuilder<T> builder(){
        return new ResultBuilder<T>();
    }

    /**
     * 返回结果构建器
     */
    public static class ResultBuilder<T>{
        private int code;
        private String message;
        private boolean success;
        private T data;
        /** 设置响应的状态码 */
        public ResultBuilder code(int code){
            this.code = code;
            return this;
        }
        /** 设置响应的提示信息 */
        public ResultBuilder message(String message){
            this.message = message;
            return this;
        }
        /** 设置响应数据成功状态 */
        public ResultBuilder success(boolean success){
            this.success = success;
            return this;
        }
        /** 设置响应的真实数据 */
        public ResultBuilder data(T data){
            this.data = data;
            return this;
        }
        /** 通过http状态码设置响应的状态码和响应提示信息 */
        public ResultBuilder resultCode(ResultCode resultCode){
            this.code = resultCode.code();
            this.message = resultCode.message();
            return this;
        }
        /** 构建真实响应类 */
        public Result<T> build() {
            Result<T> result = new Result<>();
            result.setCode(code);
            result.setMessage(message);
            result.setSuccess(success);
            result.setTimestamp(Instant.now());
            result.setData(data);
            return result;
        }
    }
}
