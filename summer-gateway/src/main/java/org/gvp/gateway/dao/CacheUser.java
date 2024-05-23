package org.gvp.gateway.dao;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前登录用户的缓存信息
 */
@Data
public class CacheUser implements Serializable {
    /** 当前登录成功的用户名 */
    private String username;
    /** 当前登录成功的用户ID */
    private String tokenId;
    /** 用户登录的过期时间 */
    private String expireTime;
    /** 当前登录成功的用户角色集合 */
    private List<String> roles;

}
