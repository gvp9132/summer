package org.gvp.gateway.dao;

import lombok.Data;

/**
 * 登录成功用户信息反回到前端信息类
 */
@Data
public class LoginUser {
    /** 登录用户名 */
    private String username;
    /** 用户登录成功后的token */
    private String token;
    /** 用户登录成功后使用的tokenId */
    private String tokenId;
    /** 用户登录成功的时间 */
    private String loginTime;
    /** 用户登录成功后的过期时间 */
    private String expireTime;
}
