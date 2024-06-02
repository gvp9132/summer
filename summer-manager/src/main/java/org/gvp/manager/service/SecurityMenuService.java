package org.gvp.manager.service;

import org.gvp.common.constant.BaseService;
import org.gvp.manager.pojo.SecurityMenu;

import java.util.List;

/**
 * 菜单信息 服务层接口
 */
public interface SecurityMenuService extends BaseService<SecurityMenu> {
    public static final String USER_MENU_KEY = "x-request-username";
    /**
     * 查找非子节点菜单列表last = false
     */
    List<SecurityMenu> findNotLast();

    /**
     * 获取所有的一级菜单
     */
    List<SecurityMenu> findFirstLevel();

}
