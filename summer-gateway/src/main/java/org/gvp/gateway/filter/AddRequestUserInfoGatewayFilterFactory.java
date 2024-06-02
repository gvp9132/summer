package org.gvp.gateway.filter;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 添加用户信息到请求头过滤器
 */
@Log4j2
@Component
public class AddRequestUserInfoGatewayFilterFactory extends AbstractGatewayFilterFactory<AddRequestUserInfoGatewayFilterFactory.Config> {
    private final String CONFIG_KEY = "headerKey";

    public AddRequestUserInfoGatewayFilterFactory() {
        super(Config.class);
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return List.of(CONFIG_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("添加用户信息到请求头: {}", config);
            return exchange.getPrincipal()
                    .flatMap(user -> {
                        ServerHttpRequest request = exchange.getRequest().mutate()
                                .headers(httpHeaders -> httpHeaders.add(config.getHeaderKey(), user.getName())).build();
                        return chain.filter(exchange.mutate().request(request).build());
                    });
        };
    }

    @Data
    public static class Config{
        /** 定义filter生效时候添加到请求头的key */
        private String headerKey;
    }
}
