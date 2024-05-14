package org.gvp.gateway.security.jwt;

/**
 * jwt 操作接口
 * @author gvp9132
 * @version V2.0
 */
public interface JsonWebToken<T> {
    public static final String TOKEN_ID_KEY = "tokenId";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 生成token方法
     * @param payload 需要token携带的有效信息
     * @return 生成的token字符串
     */
    T createToken(String payload);
    /**
     * 解析token方法
     * @param token 需要解析的token字符串
     * @return 解析后的有效信息
     */
    T parseToken(String token);
}
