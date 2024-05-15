package org.gvp.gateway.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.SecurityUser;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class SecurityUserCacheHandler implements CacheHandler<SecurityUser>{
    private final ReactiveRedisTemplate<String,SecurityUser> redisTemplate;
    private final String CACHE_USER_PREFIX = "gvp-security-user:";
    @Override
    public Mono<Boolean> save(String key, SecurityUser value) {
        log.debug("缓存用户信息到redis: {}", key);
        return redisTemplate.opsForValue().set(CACHE_USER_PREFIX + key,value);
    }

    @Override
    public Mono<SecurityUser> get(String key) {
        log.debug("从缓存获取用户信息: {}", key);
        return redisTemplate.opsForValue().get(CACHE_USER_PREFIX + key);
    }

    public void saveUser(SecurityUser user){
       save(user.getUsername(),user).subscribe(e -> log.debug("保存登录用户信息: {} ==> {}", user.getUsername(),e));
    }
}
