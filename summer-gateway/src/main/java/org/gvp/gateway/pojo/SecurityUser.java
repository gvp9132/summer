package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Table(name = "security_user",schema = "summer")
public class SecurityUser implements UserDetails {
    @Id
    private Integer id ;
    private String username ;
    private String password ;
    private boolean enabled ;
    private boolean credentialsNonExpired ;
    private boolean accountNonExpired ;
    private boolean accountNonLocked ;
    private String identity ;
    private Boolean delete ;
    @Transient
    private Collection<? extends GrantedAuthority> authorities ;
}
