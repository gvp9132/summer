package org.gvp.manager.repository;

import org.gvp.common.constant.BaseRepository;
import org.gvp.manager.pojo.SecurityMenu;

import java.util.List;

/**
 * 菜单信息 数据层接口
 */
public interface SecurityMenuRepository extends BaseRepository<SecurityMenu> {
    /**
     * 查找非子节点菜单,last=false
     */
    List<SecurityMenu> searchNotLase();

    /**
     * 查找全部一级菜单
     */
    List<SecurityMenu> searchFirstLevel();
}
