package org.gvp.gateway.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.gvp.gateway.cache.LoginUserCacheHandler;
import org.gvp.gateway.dto.LoginSuccessData;
import org.gvp.gateway.global.ResponseHandler;
import org.gvp.gateway.dto.CacheUser;
import org.gvp.gateway.pojo.SecurityRole;
import org.gvp.gateway.pojo.SecurityUser;
import org.gvp.gateway.security.jwt.JsonWebToken;
import org.gvp.gateway.security.jwt.TokenInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.Collections;

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

    /**
     * 用户登录成功后调用函数
     * 处理逻辑
     * 1. 根据用户名创建用户登录的token对象
     * 2. 将用户登录信息缓存到redis使用CacheUser
     * 3. 将用户token写入到响应头中
     *
     *
     */
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.debug("用户登录成功: {}",loginUserCacheHandler.getReactiveRedisTemplate());
        String username = authentication.getName();
        TokenInfo token = this.jsonWebToken.createToken(username);
        log.trace("根据登录用户名成用户token:{}", token);
        log.debug("当前用户权限信息: {}", authentication.getAuthorities());
        // 用户登录信息写入缓存
        CacheUser cacheUser = new CacheUser();
        cacheUser.setUsername(username);
        cacheUser.setTokenId(token.getTokenId());
        cacheUser.setExpireTime(token.getExpireTime());
        for (var authority : authentication.getAuthorities()) {
            cacheUser.getRoles().add(authority.getAuthority());
            cacheUser.getRoleIds().add(((SecurityRole)authority).getId());
        }
        Mono<Boolean> saveResult = this.loginUserCacheHandler.save(username, cacheUser);
        // 设置token写入到响应头,设置允许用户通过响应头中的Authorization获取token
        webFilterExchange.getExchange().getResponse().getHeaders().setBearerAuth(token.getToken());
        webFilterExchange.getExchange().getResponse().getHeaders().setAccessControlExposeHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));

        // 处理需要最终反回给用户的登录成功信息
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        LoginSuccessData successData = new LoginSuccessData();
        successData.setUsername(username);
        successData.setId(user.getId());
        successData.setIdentity(user.getIdentity());
        successData.setToken(JsonWebToken.TOKEN_PREFIX + token.getToken());
        successData.setTokenId(token.getTokenId());
        successData.setLoginTime(token.getCreateTime());
        successData.setExpireTime(token.getExpireTime());
        return saveResult.map(res -> {
            log.debug("用户登录信息缓存结果:{}", res);
            return Result.<LoginSuccessData>builder()
                    .resultCode(ResultCode.LOGIN_SUCCESS)
                    .success(true)
                    .data(successData).build();
        }).flatMap(resData -> this.responseHandler.responseWrite(webFilterExchange.getExchange(), resData, HttpStatus.OK));
    }
}
