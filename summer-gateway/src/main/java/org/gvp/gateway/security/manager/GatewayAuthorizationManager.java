package org.gvp.gateway.security.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.SecurityPath;
import org.gvp.gateway.service.SecurityPathService;
import org.springframework.http.server.RequestPath;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 网关权限验证管理器
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GatewayAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final SecurityPathService pathService;
    private final PathPatternParser pathPatternParser;
    private RequestPath requestPath;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        this.requestPath = context.getExchange().getRequest().getPath();
        return authentication.flatMap(this::checkHandler);
    }

    private Mono<AuthorizationDecision> checkHandler(Authentication auth){
        log.debug("用户请求权限验证: {}-{}", auth.getName(), auth.getAuthorities());
        return this.pathService.findByRoleNames(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .filter(this::checkPath)
                .next()
                .map(p -> {
                    log.debug("用户权限认证通过,允许访问资源: {}", p);
                    return new AuthorizationDecision(true);
                })
                .switchIfEmpty(Mono.defer(() -> {
                            log.debug("用户权限认证未通过,禁止访问资源");
                            return Mono.just(new AuthorizationDecision(false));
                        })
                        .defaultIfEmpty(new AuthorizationDecision(false)));
    }

    /**
     * 检查用户请求路径权限是否匹配
     * @param path 用户拥有的路径请求对象
     */
    private boolean checkPath(SecurityPath path) {
        log.debug("用户请求地址检查: 当前请求地址 ==> {}, 用户允许请求模式 : {}", this.requestPath, path.getPattern());
        return pathPatternParser.parse(path.getPattern()).matches(this.requestPath);
    }
}
