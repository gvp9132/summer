package org.gvp.manager.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.cache.CacheHandler;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.dto.NavigateMenuData;
import org.gvp.manager.pojo.SecurityMenu;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 导航菜单缓存处理器
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class NavigateMenuCacheHandler implements CacheHandler<NavigateMenuData> {
    private final RedisTemplate<String,NavigateMenuData> redisTemplate;
    /**
     * 缓存菜单树数据key前缀
     */
    private final String KEY_PREFIX = "gvp:summer:menu-navigate:";

    @Override
    public void save(String key, NavigateMenuData value) {

    }

    @Override
    public NavigateMenuData get(String key) {
        return null;
    }

    @Override
    public void saveList(String key, List<NavigateMenuData> value) {
        this.redisTemplate.opsForList().leftPushAll(KEY_PREFIX + key,value);
    }

    @Override
    public List<NavigateMenuData> getList(String key) {
        return List.of();
    }

    @Override
    public Boolean delete(String key) {
        return this.redisTemplate.delete(KEY_PREFIX + key);
    }

    public void cacheUserNavigateMenu(String username, List<NavigateMenuData> listTree) {
        log.debug("缓存用户 {} 的导航菜单",username);
        this.delete(username);
        this.saveList(username,listTree);
    }

    /**
     * 获取用户导航菜单
     * @param username 用户名
     */
    public List<NavigateMenuData> getNavigateMenu(String username) {
        return this.redisTemplate.opsForList().range(KEY_PREFIX + username,0,-1);
    }


}
