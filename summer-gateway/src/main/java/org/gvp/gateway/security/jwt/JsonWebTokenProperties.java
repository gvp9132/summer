package org.gvp.gateway.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 网关token认证配置属性类型
 *
 * @author gvp9132
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "summer.gateway.jwt")
public class JsonWebTokenProperties {
    /** jwt token秘钥 */
    private String secret;
    /** jwtId */
    private String jwtId ;
    /** jwt签发者 */
    private String issuer ;
    /** jwt接收者 */
    private String[] audience ;
    /** jwt过期时间,单位秒 */
    private long expireTime ;
}
