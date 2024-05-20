package org.gvp.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.cache.SecurityPathCacheHandler;
import org.gvp.gateway.pojo.SecurityPath;
import org.gvp.gateway.repository.SecurityPathRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityPathService {
    private final SecurityPathRepository pathRepository;

    private final SecurityPathCacheHandler cacheHandler;


    public Flux<SecurityPath> findByRoleNames(List<String> roleNames) {
        log.debug("根据角色名集合称查询路径信息:{}", roleNames);
        return pathRepository.searchByRoleNames(roleNames);
    }

    /**
     * 根据权限名字获取请求路径信息
     */
    public Flux<SecurityPath> findByAuthority(String authority) {
        log.debug("根据用户权限名字查询权限请求路径信息: {}", authority);
        return this.cacheHandler.getPathListByAuthority(authority)
                .switchIfEmpty(this.pathRepository.searchByAuthority(authority)
                        .collectList().doOnNext(e -> this.cacheHandler.savePathList(authority,e))
                        .flatMapMany(Flux::fromIterable));
    }
}
