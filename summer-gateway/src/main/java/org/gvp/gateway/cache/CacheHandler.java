package org.gvp.gateway.cache;

import reactor.core.publisher.Mono;

/**
 * 缓存处理器
 * @date 2024/04/05
 * @version 2.0
 * @auther gvp9132
 */
public interface CacheHandler<T> {
    /**
     * 保存数据到缓存
     */
    Mono<Boolean> save(String key, T value);
    /**
     * 从缓存读取数据
     * @param key 数据的缓存key
     */
    Mono<T> get(String key);

}
