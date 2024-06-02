package org.gvp.manager.service;

import jakarta.servlet.http.HttpServletRequest;
import org.gvp.common.constant.BaseService;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.pojo.SecurityUserMenu;

import java.util.List;

/**
 * 用户菜单关系表 服务层接口
 */
public interface SecurityUserMenuService extends BaseService<SecurityUserMenu> {
    public static final String USER_MENU_KEY = "x-request-username";

    /**
     * 查询用户导航菜单树
     */
    List<MenuTreeData> findUserMenuTree(HttpServletRequest request);

    /**
     * 查找全部可用菜单信息,将菜单信息装载为用户菜单树类型
     */
    List<MenuTreeData> findNavigateMenuTreeData();

    /**
     * 根据用户编号查找用户分配的菜单信息,反回格式为导航菜单数据类型
     * @param userId 用户编号
     */
    List<MenuTreeData> findNavigateMenuTreeDataByUserId(Integer userId);

    /**
     * 根据编号查找导航菜单信息,如果show为false也会找到该菜单
     */
    MenuTreeData findNavigateMenuTreeDataById(Integer id);

    /**
     * 根据用户编号获取用户菜单渲染权限树
     * @param userId 用户编号
     */
    AuthorityTreeData findAuthorityTreeData(Integer userId);

    /**
     * 更行用户菜单渲染权限树
     * @param update 权限更新对象
     */
    Integer editAuthority(AuthorityTreeUpdate update);

    /**
     * 获取全部菜单树数据
     */
    List<MenuTreeData> findMenuTreeList();

    /**
     * 根据用户编号获取用户权限菜单key列表
     * @param userId 用户编号
     */
    List<String> findUserMenuKeys(Integer userId);

    /**
     * 根据菜单的父菜单ID查找菜单树数据列表
     * @param parentId 菜单的父菜单ID
     * @return
     */
    List<MenuTreeData> findMenuTreeListByParentId(Integer parentId);
}
