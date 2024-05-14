package org.gvp.gateway.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * redis缓存处理器
 * @date 2024/04/05
 * @version 2.0
 * @auther gvp9132
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class RedisCacheHandler implements CacheHandler<Object> {
    private final ReactiveRedisTemplate<String,Object> reactiveRedisTemplate;

    @Override
    public Mono<Boolean> save(String key, Object value) {
        log.debug("save cache key:{},value:{}",key,value);
        return this.reactiveRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public Mono<Object> get(String key) {
        log.debug("get cache key:{}",key);
        return null;
    }
}
