package org.gvp.manager.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.gvp.manager.cache.MenuTreeCacheHandler;
import org.gvp.manager.cache.NavigateMenuCacheHandler;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.dto.NavigateMenuData;
import org.gvp.manager.pojo.SecurityUser;
import org.gvp.manager.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.gvp.manager.service.SecurityUserMenuService;
import org.gvp.manager.repository.SecurityUserMenuRepository;
import org.springframework.util.StringUtils;

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
    private final NavigateMenuCacheHandler navigateCacheHandler;
    private final MenuTreeCacheHandler treeCacheHandler;
    @Value("${summer.manager.config.recursion-depth:4}")
    private Integer recursionDepth;


    @Override
    public List<MenuTreeData> findRenderTree() {
        log.debug("获取全部菜单树渲染数据,用于前端生成RenderTree");
        List<MenuTreeData> renderTree = this.treeCacheHandler.getRenderTree();
        if (renderTree == null || renderTree.isEmpty()){
            renderTree = this.findMenuTreeListByParentId(PARENT_ID);
            this.findMenuTreeDataChildren(renderTree,1);
            this.treeCacheHandler.saveRenderTree(renderTree);
        }
        return renderTree;
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
            if (children != null && !children.isEmpty()) {
                menu.setChildren(children);
                this.findMenuTreeDataChildren(menu.getChildren(),depth++);
            }
        }
    }
    @Override
    public List<MenuTreeData> findMenuTreeListByParentId(Integer parentId) {
        log.debug("根据菜单的父菜单ID查找菜单树数据列表: {}",parentId);
        return this.securityUserMenuRepository.searchMenuTreeListByParentId(parentId);
    }
    @Override
    public List<String> findUserMenuKeys(Integer userId){
        log.debug("根据用户编号获取用户权限菜单key列表: {}", userId);
        return this.securityUserMenuRepository.searchUserMenuKeys(userId);
    }
    @Override
    public AuthorityTreeData findAuthorityTreeData(Integer userId) {
        log.debug("根据用户编号获取用户菜单渲染权限树: {}", userId);
        List<MenuTreeData> renderTree = this.findRenderTree();
        // 获取一级菜单的key,设置默认展开的菜单
        List<String> keys = renderTree.stream().map(MenuTreeData::getKey).toList();
        List<String> userMenuKeys = this.findUserMenuKeys(userId);
        return new AuthorityTreeData(renderTree,userMenuKeys,keys);
    }
    @Override
    public Integer editAuthority(AuthorityTreeUpdate update) {
        log.debug("更新用户菜单关系: {}", update);
        return 0;
    }
// *********************************************************************************************************************
// *********************************************** 以上为用户菜单授权支持方法 ***********************************************
// *********************************************************************************************************************
// *********************************************************************************************************************
// *********************************************************************************************************************
// *********************************************************************************************************************
// ************************************************ 以下为根据用户名查找用户菜单 ********************************************
    /** 查找用户ID */
    private SecurityUser findUserId(HttpServletRequest request){
        String username = request.getHeader(USER_MENU_KEY);
        log.debug("从用户请求头中获取用户名字: {}", username);
        if (StringUtils.hasLength(username)){
            log.debug("用户请求头中没有用户名字,返回空菜单树");
            return null;
        }
        return this.userService.findByUsername(username);
    }
    @Override
    public List<NavigateMenuData> findUserMenuTree(HttpServletRequest request) {
        SecurityUser user = this.findUserId(request);
        assert user != null;
        if (user.getId() == null){
            log.error("根据用户名查找用户菜单时,用户ID为空");
            return null;
        }
        List<NavigateMenuData> navigateMenu = this.navigateCacheHandler.getNavigateMenu(user.getUsername());
        if (navigateMenu != null){
            return navigateMenu;
        }
        // 根据用户ID查找用户分配的导航菜单信息列表
        List<NavigateMenuData> userList = this.findNavigateMenuDataByUserId(user.getId());
        // 查找并合并临时用户菜单树到最终用户菜单树中
        List<NavigateMenuData> listTree = new ArrayList<>();
        userList.forEach(m -> this.mergeNavigateMenu(this.findNavigateMenuParent(m),listTree,1));
        this.navigateCacheHandler.cacheUserNavigateMenu(user.getUsername(),listTree);
        return listTree;
    }
    @Override
    public List<NavigateMenuData> findNavigateMenuDataByUserId(Integer userId) {
        log.debug("根据用户编号查找用户分配的导航菜单信息列表: {}", userId);
        return this.securityUserMenuRepository.searchNavigateMenuByUserId(userId);
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
    private void mergeNavigateMenu(NavigateMenuData menu, List<NavigateMenuData> listTree, int depth) {
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
                            this.mergeNavigateMenu(menu.getChildren().getFirst(), r.getChildren(),depth + 1);
                        },
                        () -> listTree.add(menu)
                );
    }
    /**
     * 查找导航菜单的父菜单,是最顶层父菜单,即最后的父菜单ID为PARENT_ID
     * @param child 需要查找父菜单信息的菜单对象,这个对象会最为父菜的孩子菜单
     * @return 反回最顶层父菜单,菜单包含子菜单,但是每一层只有一个子菜单
     */
    private NavigateMenuData findNavigateMenuParent(NavigateMenuData child) {
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
            NavigateMenuData parent = this.findNavigateMenuDataById(child.getParentId());
            List<NavigateMenuData> children = new ArrayList<>();
            children.add(child);
            parent.setChildren(children);
            log.debug("查找菜单的父菜单,当前菜单: {}, 查找到的父菜单: {}", child, parent);
            child = parent;
            start++;
        }
    }
    @Override
    public NavigateMenuData findNavigateMenuDataById(Integer id) {
        log.debug("根据编号查找导航菜单信息,如果show为false也会找到该菜单: {}", id);
        return this.securityUserMenuRepository.searchNavigateMenuDataById(id);
    }

}
