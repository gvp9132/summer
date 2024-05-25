package org.gvp.gateway.repository;

import org.gvp.gateway.pojo.GatewayRoute;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GatewayRouteRepository extends R2dbcRepository<GatewayRoute,Integer>{

    @Query("SELECT `id`,`route_id`,`order`,`uri`,`metadata` FROM summer.gateway_route where `delete` = false")
    Flux<GatewayRoute> searchList();
}
