package org.gvp.gateway.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class GatewayLogoutHandler implements ServerLogoutHandler {
    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        log.debug("用户登出");
        return null;
    }
}
