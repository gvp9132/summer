package org.gvp.manager.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.gvp.manager.cache.NavigateMenuCacheHandler;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.pojo.SecurityUser;
import org.gvp.manager.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.gvp.manager.service.SecurityUserMenuService;
import org.gvp.manager.repository.SecurityUserMenuRepository;

import java.util.ArrayList;
import java.util.List;
/**
 * 用户菜单关系表 服务层接口实现类
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityUserMenuServiceImpl implements SecurityUserMenuService {
    private final SecurityUserMenuRepository securityUserMenuRepository;
    private final SecurityUserService userService;
    private final NavigateMenuCacheHandler cacheHandler;
    @Value("${summer.manager.config.recursion-depth:4}")
    private Integer recursionDepth;

    @Override
    public AuthorityTreeData findAuthorityTreeData(Integer userId) {
        log.debug("根据用户编号获取用户菜单渲染权限树: {}", userId);
        AuthorityTreeData authorityTreeData = new AuthorityTreeData();
//        List<MenuTreeData> menuTreeList = this.findMenuTreeList();
        // 根据id找到全部的一级菜单p-id 1300000000
        List<MenuTreeData> menuTreeList = this.findMenuTreeListByParentId(PARENT_ID);
        List<String> keys = menuTreeList.stream().map(m -> m.getKey()).toList();
        this.findMenuTreeDataChildren(menuTreeList,1);
        authorityTreeData.setTreeData(menuTreeList);
        authorityTreeData.setAuthorityKeys(this.findUserMenuKeys(userId));
        authorityTreeData.setExpandKeys(keys);
        this.cacheHandler.saveMenuTreeDataList(menuTreeList);
        return authorityTreeData;
    }

    /**
     * 查找菜单的子菜单
     * @param mainTreeDataList 一级菜单
     * @param depth 递归深度
     */
    private void findMenuTreeDataChildren(List<MenuTreeData> mainTreeDataList,int depth) {
        if (depth >= this.recursionDepth){
            log.error("递归深度超过最大值,请检查菜单数据是否存在循环引用");
            return;
        }
        for (MenuTreeData menu : mainTreeDataList) {
            List<MenuTreeData> children = this.findMenuTreeListByParentId(menu.getId());
            if (children != null && children.size() > 0) {
                menu.setChildren(children);
                this.findMenuTreeDataChildren(menu.getChildren(),depth++);
            }
        }
    }

    @Override
    public Integer editAuthority(AuthorityTreeUpdate update) {
        log.debug("更新用户菜单关系: {}", update);
        return 0;
    }

    @Override
    public List<String> findUserMenuKeys(Integer userId){
        log.debug("根据用户编号获取用户权限菜单key列表: {}", userId);
        return this.securityUserMenuRepository.selectUserMenuKeys(userId);
    }

    @Override
    public List<MenuTreeData> findMenuTreeList() {
        log.debug("获取全部菜单树数据");
        return this.securityUserMenuRepository.searchMenuTreeList();
    }
    @Override
    public List<MenuTreeData> findMenuTreeListByParentId(Integer parentId) {
        log.debug("根据菜单的父菜单ID查找菜单树数据列表: {}",parentId);
        return this.securityUserMenuRepository.searchMenuTreeListByParentId(parentId);
    }


    @Override
    public List<MenuTreeData> findUserMenuTree(HttpServletRequest request) {
        String username = request.getHeader(USER_MENU_KEY);
        log.debug("从用户请求头中获取用户名字: {}", username);
        if (username == null){
            log.debug("用户请求头中没有用户名字,返回空菜单树");
            return null;
        }
        List<MenuTreeData> cacheList = this.cacheHandler.getList(username);
        if (cacheList != null && cacheList.size() > 0) {
            return cacheList;
        }
        SecurityUser user = this.userService.findByUsername(username);
        if (user == null && user.getId() == null){
            log.debug("根据用户名字查找用户信息为空,返回空菜单树");
            return null;
        }
        // 1. 获取系统全部菜单
        // List<NavigateMenuTreeData> allList = this.findNavigateMenuTreeData();
        // 2. 获取用户ID可用菜单集合,使用用户id查找用户可以使用菜单信息
        List<MenuTreeData> userList = this.findNavigateMenuTreeDataByUserId(user.getId());
        // 3. 查找并合并临时用户菜单树到最终用户菜单树中
        List<MenuTreeData> listTree = new ArrayList<>();
        userList.forEach(m -> this.mergeNavigateMenu(this.findNavigateMenuParent(m),listTree,1));
        this.cacheHandler.saveList(username,listTree);
        return listTree;
    }
    /**
     * 合并导航菜单到用户菜单树,
     * 1. 如果用户菜单树中不存在该菜单,直接将menu加入到用户菜单树
     * 2. 如果用户菜单树中存在该菜单
     *      2.1 检查找到的菜单是否有子菜单,如果没有直接将menu的子菜单设置到目标菜单的子菜单
     *      2.2 检查menu是否有子菜单,如果没有不需要合并
     *      2.3 如果两者都存在子菜单,将menu的子菜单(一定是一个)合并到目标菜单的子菜单中
     * @param menu 根据菜单id查找的菜单树
     * @param listTree 最终需要反回的用户菜单树列表
     */
    private void mergeNavigateMenu(MenuTreeData menu, List<MenuTreeData> listTree, int depth) {
        if (depth > this.recursionDepth) {
            log.error("递归深度超过最大值,请检查菜单数据是否存在循环引用");
            return;
        }
        listTree.stream().filter(e -> e.getId().equals(menu.getId())).findFirst()
                .ifPresentOrElse(
                        r -> {
                            if (r.getChildren() == null || r.getChildren().isEmpty()) {
                                r.setChildren(menu.getChildren());
                                return;
                            }
                            if (menu.getChildren() == null || menu.getChildren().isEmpty()) {
                                return;
                            }
                            this.mergeNavigateMenu(menu.getChildren().get(0), r.getChildren(),depth + 1);
                        },
                        () -> listTree.add(menu)
                );
    }
    @Override
    public List<MenuTreeData> findNavigateMenuTreeData() {
        log.debug("查找全部菜单数据,数据格式为NavigateMenuTreeData");
        return this.securityUserMenuRepository.searchNavigateMenuTreeData();
    }
    @Override
    public List<MenuTreeData> findNavigateMenuTreeDataByUserId(Integer userId) {
        log.debug("根据用户编号查找用户分配的菜单信息,反回格式为导航菜单数据类型: {}", userId);
        return this.securityUserMenuRepository.searchNavigateMenuTreeDataByUserId(userId);
    }
    /**
     * 查找导航菜单的父菜单,是最顶层父菜单,即最后的父菜单ID为PARENT_ID
     * @param child 需要查找父菜单信息的菜单对象,这个对象会最为父菜的孩子菜单
     * @return 反回最顶层父菜单,菜单包含子菜单,但是每一层只有一个子菜单
     */
    private MenuTreeData findNavigateMenuParent(MenuTreeData child) {
        int start = 0;
        while (true) {
            if (start > this.recursionDepth) {
                log.error("查找用户菜单树时,循环深度过大,请检查代码逻辑");
            }
            if (child.getParentId() == null) {
                log.error("菜单信息不全,没有找到父菜单: {}", child);
                throw new RuntimeException("菜单信息不全");
            }
            if (PARENT_ID.equals(child.getParentId())) {
                log.debug("当前菜单为最顶层菜单");
                return child;
            }
            MenuTreeData parent = this.findNavigateMenuTreeDataById(child.getParentId());
            List<MenuTreeData> children = new ArrayList<>();
            children.add(child);
            parent.setChildren(children);
            log.debug("查找菜单的父菜单,当前菜单: {}, 查找到的父菜单: {}", child, parent);
            child = parent;
            start++;
        }
    }
    @Override
    public MenuTreeData findNavigateMenuTreeDataById(Integer id) {
        log.debug("根据编号查找导航菜单信息,如果show为false也会找到该菜单: {}", id);
        return this.securityUserMenuRepository.searchNavigateMenuTreeDataById(id);
    }

}
