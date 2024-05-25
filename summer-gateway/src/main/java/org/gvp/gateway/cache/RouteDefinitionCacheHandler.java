package org.gvp.gateway.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class RouteDefinitionCacheHandler {
    private static final String ROUTE_DEFINITION_PREFIX = "gvp:gateway:route-definition:";
    private final ReactiveRedisTemplate<String, RouteDefinition> redisTemplate;

    public void saveRouteDefinitions(List<RouteDefinition> routeDefinitions) {
        log.debug("缓存网关路由信息: {}", routeDefinitions);
        if (routeDefinitions.isEmpty()) {
            throw new RuntimeException("需要缓存的路由信息为空");
        }
        this.redisTemplate.unlink(ROUTE_DEFINITION_PREFIX)
                .thenMany(this.redisTemplate.opsForSet().add(ROUTE_DEFINITION_PREFIX, routeDefinitions.toArray(new RouteDefinition[0])))
                .subscribe(e -> log.debug("清除并缓存网关路由信息: {}", e));

    }

    public Flux<RouteDefinition> getRouteDefinitions() {
        return this.redisTemplate.opsForSet().members(ROUTE_DEFINITION_PREFIX);
    }

}
