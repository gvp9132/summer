package org.gvp.gateway.security.config;

import lombok.RequiredArgsConstructor;
import org.gvp.gateway.security.SecurityProperties;
import org.gvp.gateway.security.handler.GatewayAuthenticationEntryPoint;
import org.gvp.gateway.security.handler.GatewayLoginFailureHandler;
import org.gvp.gateway.security.handler.GatewayLoginSuccessHandler;
import org.gvp.gateway.security.manager.GatewayAuthorizationManager;
import org.gvp.gateway.security.manager.TokenAuthenticationManager;
import org.gvp.gateway.service.SecurityUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewaySecurityConfig {
    private static final String PASSWORD_ENCODE_ID = "bcrypt";
    /**
     * 网关安全自定义配置
     */
    private final SecurityProperties properties;
    /**
     * 用户服务接口实现类
     */
    private final SecurityUserService userDetailsService;
    /**
     * 网关安全上下文件
     */
    private final ServerSecurityContextRepository securityContextRepository;
    /**
     * 用户请求资源权限验证管理器
     */
    private final GatewayAuthorizationManager authorizationManager;
    /**
     * 用户登录成功处理器
     */
    private final GatewayLoginSuccessHandler gatewayLoginSuccessHandler;
    /**
     * 用户登录失败处理器
     */
    private final GatewayLoginFailureHandler gatewayLoginFailureHandler;
    /**
     * 用户认证入口点,用户身份认证失败处理
     */
    private final GatewayAuthenticationEntryPoint authenticationEntryPoint;


    /**
     * 配置安全拦截机制
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .securityContextRepository(this.securityContextRepository)
                .authenticationManager(this.authenticationManager())
                .authorizeExchange(exchange ->
                        exchange
                                // 静态资源无需认证
                                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .pathMatchers(this.properties.getIgnoreUrls()).permitAll()
                                .anyExchange()
                                .access(this.authorizationManager)
                )
                .formLogin(login ->
                        login
                                .authenticationSuccessHandler(this.gatewayLoginSuccessHandler)
                                .authenticationFailureHandler(this.gatewayLoginFailureHandler)
                                .authenticationEntryPoint(this.authenticationEntryPoint)
                                .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login"))
                                .authenticationManager(this.authenticationManager())
                                .securityContextRepository(this.securityContextRepository)
                )
        ;

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.cors(corsSpec -> corsSpec.configurationSource(this.configurationSource()));
        return http.build();
    }

    /**
     * 用户认证管理器
     */
    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        managers.add(new TokenAuthenticationManager());
        // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(this.userDetailsService));
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();
        encoders.put(PASSWORD_ENCODE_ID, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(PASSWORD_ENCODE_ID, encoders);
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 接收cookie
        config.setAllowedOrigins(this.properties.getAllowedOrigins());
        config.addAllowedHeader("*"); // 允许任何头
        config.addAllowedMethod("*");// 允许任何方法（post、get等）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
