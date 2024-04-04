package org.gvp.gateway.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关安全上下文存储
 * @version 2.0
 */
@Log4j2
@Repository
public class GatewaySecurityContextRepository implements ServerSecurityContextRepository {
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.debug("保存网关安全上下文: {}",context);
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.debug("加载网关安全上下文: {}",exchange.getRequest().getPath());
        return Mono.empty();
    }
}
