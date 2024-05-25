package org.gvp.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.GatewayRouteFactory;
import org.gvp.gateway.repository.GatewayRouteFactoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Log4j2
@Service
@RequiredArgsConstructor
public class GatewayRouteFactoryService {
    private final GatewayRouteFactoryRepository routeFactoryRepository;

    public Flux<GatewayRouteFactory> findByRouteId(Integer routeId){
        log.debug("根据路由编号查找路由工厂信息集合: {}", routeId);
        return this.routeFactoryRepository.searchByRouteIdAndDeleteFalse(routeId);
    }
}
