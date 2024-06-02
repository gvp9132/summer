package org.gvp.manager.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.gvp.common.http.Result;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.gvp.manager.dto.MenuTreeData;
import org.gvp.manager.dto.NavigateMenuData;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.gvp.common.constant.BaseController;
import org.gvp.common.annotation.Operation;
import org.gvp.manager.service.SecurityUserMenuService;
import org.gvp.manager.pojo.SecurityUserMenu;
import java.util.List;

@RestController
@RequestMapping("/manager/admin/user-menu")
@RequiredArgsConstructor
public class SecurityUserMenuController implements BaseController<SecurityUserMenu> {
    private final SecurityUserMenuService securityUserMenuService;
    /**
     * 获取用户导航菜单树数据
     */
    @GetMapping("/tree/navigate")
    public Result<List<NavigateMenuData>> queryUserMenuTree(HttpServletRequest request){
        return Result.ok(this.securityUserMenuService.findUserMenuTree(request));
    }
    @Operation(value = "根据用户编号获取用户菜单处理权限树", param = "用户编号")
    @GetMapping("/authority-tree/{userId:\\d+}")
    public Result<AuthorityTreeData> queryAuthorityTreeData(@PathVariable Integer userId){
        return Result.ok(this.securityUserMenuService.findAuthorityTreeData(userId));
    }

    @Operation(value = "更新用户菜单关系", param = "权限更新对象")
    @PostMapping("/authority-tree")
    public Result<Integer> updateAuthority(@RequestBody AuthorityTreeUpdate update) {
        return Result.ok(this.securityUserMenuService.editAuthority(update));
    }
}
