package org.gvp.gateway.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.gvp.gateway.cache.LoginUserCacheHandler;
import org.gvp.gateway.dao.CacheUser;
import org.gvp.gateway.security.jwt.JsonWebToken;
import org.gvp.gateway.security.jwt.TokenInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 用户登录成功处理器,处理用户登录成功后的逻辑操作
 *
 * @version 2.0
 * @auther gvp9132
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GatewayLoginSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final ResponseHandler responseHandler;
    private final JsonWebToken<TokenInfo> jsonWebToken;
    private final LoginUserCacheHandler loginUserCacheHandler;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.debug("用户登录成功: {}",loginUserCacheHandler.getReactiveRedisTemplate());
        String username = authentication.getName();
        TokenInfo token = this.jsonWebToken.createToken(username);
        log.debug("根据登录用户名成用户token:{}", token);
        log.debug("当前用户权限信息: {}", authentication.getAuthorities());
        CacheUser cacheUser = new CacheUser();
        cacheUser.setUsername(username);
        cacheUser.setTokenId(token.getTokenId());
        cacheUser.setExpireTime(token.getExpireTime().toString());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        cacheUser.setRoles(roles);

        webFilterExchange.getExchange().getResponse().getHeaders().setBearerAuth(token.getToken());
        // 设置允许用户通过响应头中的Authorization获取token
        webFilterExchange.getExchange().getResponse().getHeaders().setAccessControlExposeHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));
        token.setToken("Bearer " + token.getToken());
        Mono<Boolean> saveResult = this.loginUserCacheHandler.save(username, cacheUser);
        return saveResult.map(res -> {
            log.debug("用户登录信息缓存结果:{}", res);
            return Result.<TokenInfo>builder()
                    .resultCode(ResultCode.LOGIN_SUCCESS)
                    .success(true)
                    .data(token).build();
        }).flatMap(resData -> this.responseHandler.responseWrite(webFilterExchange.getExchange(), resData, HttpStatus.OK));
    }
}
