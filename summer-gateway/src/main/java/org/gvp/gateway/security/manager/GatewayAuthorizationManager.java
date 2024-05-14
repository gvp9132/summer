package org.gvp.gateway.security.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 网关权限验证管理器 
 */
@Log4j2
@Component
public class GatewayAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        log.debug("用户请求权限验证: {}",context.getExchange().getRequest().getPath());

        return authentication.flatMap(
                auth -> {
                    log.debug("用户权限验证: {}",auth.getName());
                    log.debug("auth: {}",auth.getAuthorities());
                    return Mono.just(new AuthorizationDecision(true));
                }
        );

//        return Mono.just(new AuthorizationDecision(false));
    }
}
