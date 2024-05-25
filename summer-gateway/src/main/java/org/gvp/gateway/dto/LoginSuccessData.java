package org.gvp.gateway.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录成功反回信息包装类
 */
@Data
public class LoginSuccessData implements Serializable {
    private Integer id;
    private String username;
    /**
     * 用户身份
     */
    private String identity;
    /**
     * 用户认证token
     */
    private String token;
    private String tokenId;
    /** 登录时间 */
    private LocalDateTime loginTime;
    /** 登录过期时间 */
    private LocalDateTime expireTime;
}
