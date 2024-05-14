package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 * 安全用户->角色实体
 */
@Data
@Table(name = "security_role",schema = "summer")
public class SecurityRole implements GrantedAuthority {
    @Id
    private Integer id ;
    /**
     * 权限或角色
     */
    private String authority;
    /**
     * 用户是否删除
     */
//    private Boolean delete ;
}
