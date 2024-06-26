package org.gvp.manager.repository.impl;

import lombok.RequiredArgsConstructor;

import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.dto.NavigateMenuData;
import org.springframework.stereotype.Repository;
import org.gvp.manager.repository.SecurityUserMenuRepository;
import org.gvp.manager.mapper.SecurityUserMenuMapper;

import java.util.List;
/**
 * 用户菜单关系表 数据层接口实现类
 */
@Repository
@RequiredArgsConstructor
public class SecurityUserMenuRepositoryImpl implements SecurityUserMenuRepository {
    private final SecurityUserMenuMapper securityUserMenuMapper;

    @Override
    public List<String> searchUserMenuKeys(Integer userId) {
        return this.securityUserMenuMapper.selectUserMenuKeys(userId);
    }

    @Override
    public List<MenuTreeData> searchMenuTreeListByParentId(Integer parentId) {
        return this.securityUserMenuMapper.selectMenuTreeListByParentId(parentId);
    }

    @Override
    public List<MenuTreeData> searchMenuTreeList() {
        return this.securityUserMenuMapper.selectMenuTreeList();
    }

    @Override
    public List<NavigateMenuData> searchNavigateMenuByUserId(Integer userId) {
        return this.securityUserMenuMapper.selectNavigateMenuByUserId(userId);
    }

    @Override
    public NavigateMenuData searchNavigateMenuDataById(Integer id) {
        return null;
    }


//    @Override
//    public List<MenuTreeData> searchNavigateMenuTreeDataByUserId(Integer userId) {
//        return this.securityUserMenuMapper.selectMenuTreeDataByUserId(userId);
//    }
//
//    @Override
//    public MenuTreeData searchNavigateMenuTreeDataById(Integer id) {
//        return this.securityUserMenuMapper.selectMenuTreeDataById(id);
//    }
}
