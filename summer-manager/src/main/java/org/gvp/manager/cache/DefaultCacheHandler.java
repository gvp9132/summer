package org.gvp.manager.cache;

import lombok.RequiredArgsConstructor;
import org.gvp.common.cache.CacheHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultCacheHandler implements CacheHandler<Object> {
    private final RedisTemplate<String,Object> redisTemplate;


    @Override
    public void save(String key, Object value) {
        this.redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public void saveList(String key, List<Object> value) {

    }

    @Override
    public List<Object> getList(String key) {
        return List.of();
    }

    @Override
    public Boolean delete(String key) {
        return null;
    }
}
