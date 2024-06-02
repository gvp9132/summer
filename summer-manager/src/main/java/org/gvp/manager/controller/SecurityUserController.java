package org.gvp.manager.controller;

import lombok.RequiredArgsConstructor;

import org.gvp.common.http.Result;
import org.gvp.manager.feign.RemoteUserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.gvp.common.constant.BaseController;
import org.gvp.common.annotation.Operation;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityUserService;
import org.gvp.manager.pojo.SecurityUser;
import java.util.List;

@RestController
@RequestMapping("/manager/admin/user")
@RequiredArgsConstructor
public class SecurityUserController implements BaseController<SecurityUser> {
    private final SecurityUserService securityUserService;
    private final RemoteUserService remoteUserService;

    @GetMapping("/test")
    public Object test(){
        return this.remoteUserService.passwordEncryption("123");
    }

    @PutMapping
    @Operation(value = "添加用户信息表数据",param = "用户信息表实体类对象")
    @Override
    public Result<Integer> add(@RequestBody SecurityUser pojo) {
        return Result.ok(this.securityUserService.save(pojo));
    }

    @PutMapping("/return")
    @Operation(value = "添加用户信息表数据,并反回新数据",param = "用户信息表实体类对象")
    @Override
    public Result<SecurityUser> addAndReturn(@RequestBody SecurityUser pojo) {
        return Result.ok(this.securityUserService.saveAndReturn(pojo));
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(value = "删除用户信息表数据", param = "id: 用户信息表数据编号")
    @Override
    public Result<Integer> deleteById(@PathVariable Integer id) {
        return Result.ok(this.securityUserService.removeById(id));
    }

    @PostMapping
    @Operation(value = "使用编号更新用户信息表数据", param = "pojo: 用户信息表数据,数据编号(ID)必须存在")
    @Override
    public Result<Integer> update(@RequestBody SecurityUser pojo) {
        return Result.ok(this.securityUserService.edit(pojo));
    }

    @PostMapping("/return")
    @Operation(value = "使用编号更新用户信息表数据,更新之后反回新的数据", param = "pojo: 用户信息表数据,数据编号(ID)必须存在")
    @Override
    public Result<SecurityUser> updateAndReturn(@RequestBody SecurityUser pojo) {
        return Result.ok(this.securityUserService.editAndReturn(pojo));
    }

    @GetMapping("/{id:\\d+}")
    @Operation(value = "根据ID查找用户信息表数据", param = "id: 用户信息表数据ID")
    @Override
    public Result<SecurityUser> queryById(@PathVariable Integer id) {
        return Result.ok(this.securityUserService.findById(id));
    }

    @GetMapping("/list")
    @Operation("获取用户信息表数据列表")
    @Override
    public Result<List<SecurityUser>> queryList() {
        return Result.ok(this.securityUserService.findList());
    }

    @GetMapping("/limit/{start:\\d+}/{end:\\d+}")
    @Operation(value = "查找指定区间的用户信息表数据列表",param = "start: 起始位置, end: 结束位置")
    @Override
    public Result<List<SecurityUser>> queryLimit(@PathVariable int start,@PathVariable int end) {
        return Result.ok(securityUserService.findLimit(start, end));
    }

    @GetMapping("/count")
    @Operation("查找用户信息表全部数据列表")
    @Override
    public Result<Long> queryCount() {
        return Result.ok(this.securityUserService.findCount());
    }

    @GetMapping("/page/{page:\\d+}/{size:\\d+}")
    @Operation(value = "分页查找用户信息表数据", param = "currentPage: 当前页, pageSize: 页大小")
    @Override
    public Result<Page<SecurityUser>> queryPage(@PathVariable int page,@PathVariable int size) {
        return Result.ok(this.securityUserService.findPage(page, size));
    }

}
