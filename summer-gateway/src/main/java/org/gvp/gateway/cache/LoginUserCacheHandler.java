package org.gvp.gateway.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.CacheUser;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 登录用户缓存处理器
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class LoginUserCacheHandler implements CacheHandler<CacheUser>{
    private static final String CACHE_USER_PREFIX = "gvp:login-user:";
    private final ReactiveRedisTemplate<String,CacheUser> reactiveRedisTemplate;
    @Override
    public Mono<Boolean> save(String key, CacheUser value) {
        log.debug("缓存登录用户信息到缓存: {}-{}",key,value);
        return this.reactiveRedisTemplate.opsForValue().set(CACHE_USER_PREFIX + key,value);
    }

    @Override
    public Mono<CacheUser> get(String key) {
       log.trace("从缓存读取用户登录信息: {}",key);
        return this.reactiveRedisTemplate.opsForValue().get(CACHE_USER_PREFIX + key);
    }

    public ReactiveRedisTemplate<String,CacheUser> getReactiveRedisTemplate(){
        return this.reactiveRedisTemplate;
    }
}
