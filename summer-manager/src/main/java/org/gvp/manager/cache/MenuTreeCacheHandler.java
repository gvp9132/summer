package org.gvp.manager.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.cache.CacheHandler;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.pojo.SecurityMenu;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单权限树缓存处理器
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class MenuTreeCacheHandler implements CacheHandler<MenuTreeData> {
    private final RedisTemplate<String,MenuTreeData> redisTemplate;
    /**
     * 缓存菜单树数据key前缀
     */
    private final String KEY_PREFIX = "gvp:summer:menu-tree:";

    @Override
    public void save(String key, MenuTreeData value) {

    }

    @Override
    public MenuTreeData get(String key) {
        return null;
    }

    @Override
    public void saveList(String key, List<MenuTreeData> value) {

    }

    @Override
    public List<MenuTreeData> getList(String key) {
        return List.of();
    }

    @Override
    public Boolean delete(String key) {
        return this.redisTemplate.delete(KEY_PREFIX + key);
    }

    /**
     * 保存数据库中查找的渲染树
     * @param menuTreeList renderTree对象
     */
    public void saveRenderTree(List<MenuTreeData> menuTreeList) {
        log.debug("保存数据库中查找的菜单渲染树");
        this.delete("render-tree");
        this.redisTemplate.opsForList().leftPushAll(KEY_PREFIX + "render-tree",menuTreeList);
    }

    /**
     * 获取缓存中的前端渲染数
     */
    public List<MenuTreeData> getRenderTree() {
        log.debug("获取缓存中的前端菜单渲染数");
        return this.redisTemplate.opsForList().range(KEY_PREFIX + "render-tree",0,-1);
    }
}
