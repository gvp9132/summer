package org.gvp.gateway.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * 用户登录成功处理器,处理用户登录成功后的逻辑操作
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GatewayLoginSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final ResponseHandler responseHandler;
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
       log.debug("用户登录成功");
        Result build = Result.builder()
                .resultCode(ResultCode.SUCCESS)
                .build();

        return this.responseHandler.responseWrite(webFilterExchange.getExchange(), build);
    }
}
