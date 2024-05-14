package org.gvp.gateway.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关访问权限不足拒绝处理器
 * @version 2.0
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GatewayAccessDeniedHandler implements ServerAccessDeniedHandler {
    private final ResponseHandler responseHandler;
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
       log.warn("用户权限不足,拒绝访问");
        return responseHandler.responseWrite(exchange, Result.resultCoder(ResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
    }
}
