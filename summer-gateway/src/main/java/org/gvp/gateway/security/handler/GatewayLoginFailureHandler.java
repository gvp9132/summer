package org.gvp.gateway.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.gvp.gateway.global.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 用户登录失败处理器,处理用户登录失败逻辑
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GatewayLoginFailureHandler implements ServerAuthenticationFailureHandler {
    private final ResponseHandler responseHandler;
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange exchange, AuthenticationException exception) {
        log.error("用户登录失败",exception);
        Result<String> result = Result.fail(null);
        switch (exception){
            case UsernameNotFoundException une:
                result.setResponseCode(ResultCode.LOGIN_FAILURE_USER_NOT_FOUND);
                break;
            case BadCredentialsException bce:
                result.setResponseCode(ResultCode.LOGIN_FAILURE_PASSWORD_ERROR);
                break;
            case DisabledException de:
                result.setResponseCode(ResultCode.LOGIN_FAILURE_USER_DISABLED);
                break;
            case LockedException le:
                result.setResponseCode(ResultCode.LOGIN_FAILURE_USER_LOCKED);
                break;
            case AccountExpiredException aee:
                result.setResponseCode(ResultCode.LOGIN_FAILURE_USER_EXPIRED);
                break;
            case CredentialsExpiredException cee:
                result.setResponseCode(ResultCode.LOGIN_FAILURE_PASSWORD_EXPIRED);
                break;
            default:
                result.setResponseCode(ResultCode.LOGIN_FAILURE);
                break;
        }
        return responseHandler.responseWrite(exchange.getExchange(), result,HttpStatus.PAYMENT_REQUIRED);
    }
}
