package org.gvp.manager.controller;

import lombok.RequiredArgsConstructor;

import org.gvp.common.http.Result;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.gvp.common.constant.BaseController;
import org.gvp.common.annotation.Operation;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityUserRoleService;
import org.gvp.manager.pojo.SecurityUserRole;

@RestController
@RequestMapping("/manager/admin/user-role")
@RequiredArgsConstructor
public class SecurityUserRoleController implements BaseController<SecurityUserRole> {
    private final SecurityUserRoleService securityUserRoleService;


    @Operation(value = "根据用户编号获取用户角色处理权限树", param = "用户编号")
    @GetMapping("/authority-tree/{userId:\\d+}")
    public Result<AuthorityTreeData> queryAuthorityTreeData(@PathVariable Integer userId) {
        return Result.ok(securityUserRoleService.findAuthorityTreeData(userId));
    }

    @Operation(value = "更新用户角色关系", param = "权限更新对象")
    @PostMapping("/authority-tree")
    public Result<Integer> updateAuthority(@RequestBody AuthorityTreeUpdate update) {
        return Result.ok(securityUserRoleService.editAuthority(update));
    }

}
