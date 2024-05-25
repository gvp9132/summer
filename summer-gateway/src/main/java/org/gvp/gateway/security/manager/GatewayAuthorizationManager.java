package org.gvp.gateway.security.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.SecurityPath;
import org.gvp.gateway.service.SecurityPathService;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
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
    private HttpMethod requestMethod;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        this.requestPath = context.getExchange().getRequest().getPath();
        this.requestMethod = context.getExchange().getRequest().getMethod();
        log.debug("用户请求权限验证: 请求方法: {}, 请求路径: {} ",this.requestMethod,this.requestPath);
        return authentication
                .flatMap(auth -> Flux.fromIterable(auth.getAuthorities())
                        .flatMap(e -> this.pathService.findByAuthority(e.getAuthority()))
                        .any(this::checkPath)
                )
                .map(AuthorizationDecision::new)
                .switchIfEmpty(Mono.just(new AuthorizationDecision(false)));
//        return authentication.flatMap(this::checkHandler);
    }

    private Mono<AuthorizationDecision> checkHandler(Authentication auth) {
        log.debug("用户请求权限验证,用户名: {}, 用户角色信息: {}", auth.getName(), auth.getAuthorities());
        return Flux.fromIterable(auth.getAuthorities())
                .flatMap(e -> this.pathService.findByAuthority(e.getAuthority()))
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
     *
     * @param path 用户拥有的路径请求对象
     */
    private boolean checkPath(SecurityPath path) {
        log.debug("用户请求权限验证: {}--{}, 当前用户请求路径信息: {} ",this.requestMethod,this.requestPath,path);
        if(pathPatternParser.parse(path.getPattern()).matches(this.requestPath) && checkMethod(path.getMethod())){
            log.info("用户请求路径权限和请求方法验证通过: {}",path);
            return true;
        }
        return false;
    }

    private boolean checkMethod(String method){
        if ("ANY".equals(method) || this.requestMethod.matches(method)) {
            log.debug("用户请求方法验证成功");
            return true;
        }
        return false;
    }
}