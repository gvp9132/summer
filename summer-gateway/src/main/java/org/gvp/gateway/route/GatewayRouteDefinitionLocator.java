package org.gvp.gateway.route;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.service.GatewayRouteService;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Log4j2
@Repository
@RequiredArgsConstructor
public class GatewayRouteDefinitionLocator implements RouteDefinitionLocator {
    private final GatewayRouteService routeService;
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return this.routeService.findRouteDefinition();
    }

//    @Override
//    public Mono<Void> save(Mono<RouteDefinition> route) {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> delete(Mono<String> routeId) {
//        return null;
//    }
}