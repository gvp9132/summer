package org.gvp.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.dto.NavigateMenuData;
import org.gvp.manager.pojo.SecurityUserMenu;

import java.util.List;

/**
* 用户菜单关系表 mp映射接口
*/
@Mapper
public interface SecurityUserMenuMapper extends BaseMapper<SecurityUserMenu> {


    /**
     * 根据编号查找导航菜单信息,如果show为false也会找到该菜单
     */
    MenuTreeData selectMenuTreeDataById(Integer id);

    /**
     * 查找菜单树数据
     */
    List<MenuTreeData> selectMenuTreeList();

    /**
     * 根据用户编号获取用户权限菜单key列表
     * @param userId 用户编号
     */
    List<String> selectUserMenuKeys(Integer userId);

    /**
     * 根据菜单的父菜单ID查找菜单树数据列表(菜单权限树)
     * @param parentId 父菜单ID
     */
    List<MenuTreeData> selectMenuTreeListByParentId(Integer parentId);


    List<NavigateMenuData> selectNavigateMenuByUserId(Integer userId);
}
