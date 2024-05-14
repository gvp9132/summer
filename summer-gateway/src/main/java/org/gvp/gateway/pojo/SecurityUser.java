package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全用户实体
 */
@Data
@Table(name = "security_user",schema = "summer")
public class SecurityUser implements UserDetails {
    @Id
    private Integer id ;
    /**
     * 用户名
     */
    private String username ;
    /**
     * 用户密码
     */
    private String password ;
    /**
     * 用户是否启用
     */
    private boolean enabled ;
    /**
     * 用户凭证是否过期
     */
    private boolean credentialsNonExpired ;
    /**
     * 用户账户是否过期
     */
    private boolean accountNonExpired ;
    /**
     * 用户是否被锁定
     */
    private boolean accountNonLocked ;
    /**
     * 用户身份
     */
    private String identity ;
    /**
     * 用户是否删除
     */
    private Boolean delete ;
    @Transient
    private Collection<? extends GrantedAuthority> authorities ;
}
