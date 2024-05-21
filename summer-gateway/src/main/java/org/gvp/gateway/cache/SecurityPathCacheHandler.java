package org.gvp.gateway.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.SecurityPath;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Log4j2
@Component
@RequiredArgsConstructor
public class SecurityPathCacheHandler implements CacheHandler<SecurityPath>{

    private final ReactiveRedisTemplate<String,SecurityPath> redisTemplate;

    private static final String ROLE_PATH_PREFIX = "gvp:security-user-path:";

    @Override
    public Mono<Boolean> save(String key, SecurityPath value) {
        return null;
    }

    @Override
    public Mono<SecurityPath> get(String key) {
        return null;
    }


    /**
     * 根据权限名字获取请求路径列表信息
     * @param authority 缓存的权限名字
     * @return 返回请求路径列表信息
     */
    public Flux<SecurityPath> getPathListByAuthority(String authority) {
        log.trace("从缓存获取角色 [{}] 路径数据",authority);
        return redisTemplate.opsForList().range(ROLE_PATH_PREFIX + authority,0,-1);
    }

    public void savePathList(String authority, List<SecurityPath> pathList) {
        if (pathList == null || pathList.isEmpty()){
            // TODO: 这里目前没办法缓存空数据,每次会查找数据库,需要处理
            log.warn("当前缓存的角色路径关系中路径信息为空: {} - {}",authority, pathList);
            return;
        }
        this.clearPathList(authority);
        this.redisTemplate.opsForList().leftPushAll(ROLE_PATH_PREFIX + authority, pathList)
                .subscribe( r -> log.debug("保存角色 [{}] 路径数据到缓存: {}", authority,r));
    }

    public void clearPathList(String authority){
        this.redisTemplate.unlink(ROLE_PATH_PREFIX + authority).subscribe(e -> log.debug("清除角色 [{}] 路径数据",authority));
    }

    /**
     * 删除缓存中的所有角色和请求路径关系
     */
    public Mono<Long> clearCache() {
        return this.scanKeys(ROLE_PATH_PREFIX + "*")
                .doOnNext( e -> log.debug("准备删除的key: {}" , e))
                .flatMap(this.redisTemplate::unlink).count();

    }


    @Override
    public Flux<String> scanKeys(String pattern){
        return this.redisTemplate.scan(ScanOptions.scanOptions().match(pattern).count(100).build());
    }
}
