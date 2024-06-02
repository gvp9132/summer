package org.gvp.manager.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.cache.CacheHandler;
import org.gvp.manager.dto.MenuTreeData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Log4j2
@Component
@RequiredArgsConstructor
public class NavigateMenuCacheHandler implements CacheHandler<MenuTreeData> {
    private final RedisTemplate<String,MenuTreeData> redisTemplate;
    private final String KEY_PREFIX = "gvp:summer:user-menu:";
    @Override
    public void save(String key, MenuTreeData value) {

    }

    @Override
    public MenuTreeData get(String key) {
        return null;
    }


    @Override
    public void saveList(String key, List<MenuTreeData> value) {
        Boolean delete = this.delete(key);
        log.debug("缓存list数据: {}:{} -- {}",delete,key,value);
        this.redisTemplate.opsForList().leftPushAll( KEY_PREFIX + key,value);
    }

    @Override
    public List<MenuTreeData> getList(String key) {
        log.trace("从缓存读取list数据: {}",key);
        return this.redisTemplate.opsForList().range(KEY_PREFIX + key,0,-1);
    }

    @Override
    public Boolean delete(String key) {
        log.debug("删除缓存: {}", key);
         return this.redisTemplate.delete(KEY_PREFIX + key);
    }

    /**
     * 缓存权限菜单树数据,全部菜单
     * @param menuTreeList
     */
    public void saveMenuTreeDataList(List<MenuTreeData> menuTreeList) {
//        this.saveList("menu-tree-list",menuTreeList);
    }
}
