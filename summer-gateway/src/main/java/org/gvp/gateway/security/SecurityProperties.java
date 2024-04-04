package org.gvp.gateway.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关安全配置属性类型
 *
 * @author gvp9132
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "summer.gateway.security")
public class SecurityProperties {

    /** 网关认证忽略保护的地址 */
    private String[] ignoreUrls;
    /** 网关认证允许的跨域地址(源) */
    private List<String> allowedOrigins;
}
