package org.gvp.gateway.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 用户认证入口点,处理用户认证失败逻辑
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GatewayAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final ResponseHandler responseHandler;
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        log.error("用户身份认证失败");
        return responseHandler.responseWrite(exchange, Result.resultCoder(ResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
}
