package org.gvp.manager.repository;

import org.gvp.common.constant.BaseRepository;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.pojo.SecurityUserMenu;

import java.util.List;

/**
 * 用户菜单关系表 数据层接口
 */
public interface SecurityUserMenuRepository extends BaseRepository<SecurityUserMenu> {
    /**
     * 查找全部可用菜单信息,将菜单信息装载为用户菜单树类型
     */
    List<MenuTreeData> searchNavigateMenuTreeData();

    /**
     * 根据用户编号查找用户分配的菜单信息,反回格式为导航菜单数据类型
     * @param userId 用户编号
     */
    List<MenuTreeData> searchNavigateMenuTreeDataByUserId(Integer userId);

    /**
     * 根据编号查找导航菜单信息,如果show为false也会找到该菜单
     */
    MenuTreeData searchNavigateMenuTreeDataById(Integer id);

    /**
     * 获取全部菜单树数据
     */
    List<MenuTreeData> searchMenuTreeList();

    /**
     * 根据用户编号获取用户权限菜单key列表
     * @param userId 用户编号
     */
    List<String> selectUserMenuKeys(Integer userId);

    /**
     * 根据菜单的父菜单ID查找菜单树数据列表
     * @param parentId 父菜单ID
     */
    List<MenuTreeData> searchMenuTreeListByParentId(Integer parentId);
}
