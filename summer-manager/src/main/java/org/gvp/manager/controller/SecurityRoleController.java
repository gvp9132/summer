package org.gvp.manager.controller;

import lombok.RequiredArgsConstructor;

import org.gvp.common.http.Result;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.gvp.common.constant.BaseController;
import org.gvp.common.annotation.Operation;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityRoleService;
import org.gvp.manager.pojo.SecurityRole;
import java.util.List;

@RestController
@RequestMapping("/manager/admin/role")
@RequiredArgsConstructor
public class SecurityRoleController implements BaseController<SecurityRole> {
    private final SecurityRoleService securityRoleService;

    @PutMapping
    @Operation(value = "添加角色信息表数据",param = "角色信息表实体类对象")
    @Override
    public Result<Integer> add(SecurityRole pojo) {
        return Result.ok(this.securityRoleService.save(pojo));
    }

    @PutMapping("/return")
    @Operation(value = "添加角色信息表数据,并反回新数据",param = "角色信息表实体类对象")
    @Override
    public Result<SecurityRole> addAndReturn(SecurityRole pojo) {
        return Result.ok(this.securityRoleService.saveAndReturn(pojo));
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(value = "删除角色信息表数据", param = "id: 角色信息表数据编号")
    @Override
    public Result<Integer> deleteById(@PathVariable Integer id) {
        return Result.ok(this.securityRoleService.removeById(id));
    }

    @PostMapping
    @Operation(value = "使用编号更新角色信息表数据", param = "pojo: 角色信息表数据,数据编号(ID)必须存在")
    @Override
    public Result<Integer> update(SecurityRole pojo) {
        return Result.ok(this.securityRoleService.edit(pojo));
    }

    @PostMapping("/return")
    @Operation(value = "使用编号更新角色信息表数据,更新之后反回新的数据", param = "pojo: 角色信息表数据,数据编号(ID)必须存在")
    @Override
    public Result<SecurityRole> updateAndReturn(SecurityRole pojo) {
        return Result.ok(this.securityRoleService.editAndReturn(pojo));
    }

    @GetMapping("/{id:\\d+}")
    @Operation(value = "根据ID查找角色信息表数据", param = "id: 角色信息表数据ID")
    @Override
    public Result<SecurityRole> queryById(@PathVariable Integer id) {
        return Result.ok(this.securityRoleService.findById(id));
    }

    @GetMapping("/list")
    @Operation("获取角色信息表数据列表")
    @Override
    public Result<List<SecurityRole>> queryList() {
        return Result.ok(this.securityRoleService.findList());
    }

    @GetMapping("/limit/{start:\\d+}/{end:\\d+}")
    @Operation(value = "查找指定区间的角色信息表数据列表",param = "start: 起始位置, end: 结束位置")
    @Override
    public Result<List<SecurityRole>> queryLimit(@PathVariable int start,@PathVariable int end) {
        return Result.ok(securityRoleService.findLimit(start, end));
    }

    @GetMapping("/count")
    @Operation("查找角色信息表全部数据列表")
    @Override
    public Result<Long> queryCount() {
        return Result.ok(this.securityRoleService.findCount());
    }

    @GetMapping("/page/{page:\\d+}/{size:\\d+}")
    @Operation(value = "分页查找角色信息表数据", param = "currentPage: 当前页, pageSize: 页大小")
    @Override
    public Result<Page<SecurityRole>> queryPage(@PathVariable int page,@PathVariable int size) {
        return Result.ok(this.securityRoleService.findPage(page, size));
    }

}
