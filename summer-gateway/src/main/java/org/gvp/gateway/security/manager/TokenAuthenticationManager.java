package org.gvp.gateway.security.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * Token认证管理器
 * @since 1.0.0
 */
@Log4j2
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.debug("Token认证管理器: {}",authentication);
        return Mono.empty();
    }
}
