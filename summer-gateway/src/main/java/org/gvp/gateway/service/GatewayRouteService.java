package org.gvp.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.cache.RouteDefinitionCacheHandler;
import org.gvp.gateway.pojo.GatewayRoute;
import org.gvp.gateway.repository.GatewayRouteRepository;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class GatewayRouteService {
    private final GatewayRouteRepository routeRepository;
    private final GatewayRouteFactoryService routeFactoryService;
    private final RouteDefinitionCacheHandler cacheHandler;
    private final ObjectMapper objectMapper;

    private Flux<GatewayRoute> findGatewayRouteList() {
        return this.routeRepository.searchList();
    }

    public Flux<RouteDefinition> findRouteDefinition() {
        log.trace("加载用户自定义网关路由信息");
        return this.cacheHandler.getRouteDefinitions()
                .switchIfEmpty(this.findRouteDefinitionByDDB());
    }

    private Flux<RouteDefinition> findRouteDefinitionByDDB() {
        return this.findGatewayRouteList().flatMap(route -> {
                    RouteDefinition def = new RouteDefinition();
                    def.setId(route.getRouteId());
                    def.setUri(URI.create(route.getUri()));
                    def.setOrder(route.getOrder());
                    def.setMetadata(this.metadataToHashMap(route.getMetadata()));
                    log.debug("转换路由信息: {}", def);
                    return this.routeFactoryService.findByRouteId(route.getId())
                            .flatMap(factory -> {
                                log.debug("解析路由工厂信息: {}",factory);
                                if ("predicate".equals(factory.getType())) {
                                    PredicateDefinition pd = new PredicateDefinition();
                                    pd.setArgs(this.metadataToLinkedHashMap(factory.getArgs()));
                                    pd.setName(factory.getName());
                                    log.debug("转换路由断言工厂信息: {}", pd);
                                    def.getPredicates().add(pd);
                                } else if ("filter".equals(factory.getType())) {
                                    FilterDefinition fd = new FilterDefinition();
                                    fd.setArgs(this.metadataToLinkedHashMap(factory.getArgs()));
                                    fd.setName(factory.getName());
                                    def.getFilters().add(fd);
                                } else {
                                    log.warn("没有解析到路由工厂信息: {}", factory);
                                }
                                return Mono.just(def);
                            });
                })
                .collectList()
                .doOnNext(cacheHandler::saveRouteDefinitions)
                .flatMapMany(Flux::fromIterable);
    }

    /**
     * metadata元数据(String)转换为HashMap
     */
    private HashMap<String, Object> metadataToHashMap(String metadata) {
        if (!StringUtils.hasText(metadata)) {
            return new HashMap<>();
        }
        try {
            HashMap hashMap = this.objectMapper.readValue(metadata, HashMap.class);
            return hashMap;
        } catch (JsonProcessingException e) {
            log.warn("路由元信息转换为HashMap对象出现错误: {}", metadata);
            throw new RuntimeException("路由元信息转换为HashMap对象出现错误");
        }
    }

    /**
     * metadata路由工厂(String)转换为LinkedHashMap
     */
    private LinkedHashMap<String, String> metadataToLinkedHashMap(String metadata) {
        if (!StringUtils.hasText(metadata)) {
            return new LinkedHashMap<>();
        }
        try {
            LinkedHashMap map = this.objectMapper.readValue(metadata, LinkedHashMap.class);
            return map;
        } catch (JsonProcessingException e) {
            log.warn("路由工厂参数转换为LinkedHashMap对象出现错误: {}", metadata);
            throw new RuntimeException("路由元信息转换为HashMap对象出现错误");
        }
    }
}
