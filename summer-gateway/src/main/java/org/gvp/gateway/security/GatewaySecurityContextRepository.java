package org.gvp.gateway.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.cache.LoginUserCacheHandler;
import org.gvp.gateway.pojo.CacheUser;
import org.gvp.gateway.security.jwt.JsonWebToken;
import org.gvp.gateway.security.jwt.TokenInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关安全上下文存储
 * @version 2.0
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class GatewaySecurityContextRepository implements ServerSecurityContextRepository {
    private final JsonWebToken<TokenInfo> jsonWebToken;
    private final LoginUserCacheHandler loginUserCacheHandler;
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.debug("保存网关安全上下文: {}",context);
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.debug("加载网关安全上下文: {}",exchange.getRequest().getPath());
        // 从请求头中获取token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.debug("用户请求头中的token认证信息: {}",token);
        // 验证token是否为空
        if (!StringUtils.hasLength(token)){
            log.warn("用户携带token认证信息为空");
            return Mono.empty();
        }
        // 截取token
        TokenInfo tokenInfo = this.jsonWebToken.parseToken(token);
        log.debug("解析的token信息: {}",tokenInfo);
        if(tokenInfo.isExpired()){
            log.warn("用户token已过期");
            exchange.getResponse().setStatusCode(HttpStatus.FAILED_DEPENDENCY);
            return Mono.empty();
        }
        if (!StringUtils.hasLength(tokenInfo.getUsername())){
            log.error("用户token中的用户名为空");
            return Mono.empty();
        }
        return this.createUserContext(exchange,tokenInfo);
    }

    /**
     * 创建用户上下文
     */
    private Mono<SecurityContext> createUserContext(ServerWebExchange exchange, TokenInfo tokenInfo) {
        Mono<CacheUser> cacheUser = this.loginUserCacheHandler.get(tokenInfo.getUsername());
        return cacheUser.switchIfEmpty(
                Mono.defer( () -> {
                    log.warn("用户登录信息不存在");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return Mono.empty();
                }))
                .map( user -> {
                    log.debug("用户登录信息: {}",user);
                    return new SecurityContextImpl(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getRoles(),
                            user.getRoles().stream().map(SimpleGrantedAuthority::new).toList()));
                });
    }
}
