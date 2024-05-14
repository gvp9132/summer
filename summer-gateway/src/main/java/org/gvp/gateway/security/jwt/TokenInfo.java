package org.gvp.gateway.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

/**
 * jwt生成或者解析token的信息包装类
 */
@Data
public class TokenInfo {
    /** 创建或者解析的token用户名 */
    private String username;
    /** 创建成功的token,如果是解析token操作此字段为空 */
    private String token;
    /** token的唯一标识,表示用户登录时候被其他用户强制退出 */
    private String tokenId;
    /** token创建时间 */
    private Instant createTime ;
    /** token过期时间 */
    private Instant expireTime ;
    /** token是否过期 */
    private boolean expired;
    /** 解析token的时候是否出现错误 */
    @JsonIgnore
    private boolean error;
    /** 解析token的时候出现的错误信息 */
    @JsonIgnore
    private String errorMessage;
}
