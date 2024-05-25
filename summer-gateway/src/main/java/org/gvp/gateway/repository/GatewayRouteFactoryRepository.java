package org.gvp.gateway.repository;

import org.gvp.gateway.pojo.GatewayRouteFactory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GatewayRouteFactoryRepository extends R2dbcRepository<GatewayRouteFactory,Integer> {

    /**
     * 根据路由数据编号查找路由工厂信息集合
     */
    @Query("select `type`,`name`,`args`,`metadata` from gateway_route_factory where `delete` = false and route_id = :routeId")
    Flux<GatewayRouteFactory> searchByRouteIdAndDeleteFalse(@Param("routeId") Integer routeId);
}
