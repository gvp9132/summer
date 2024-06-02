package org.gvp.manager.controller;

import lombok.RequiredArgsConstructor;

import org.gvp.common.http.Result;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.gvp.common.constant.BaseController;
import org.gvp.common.annotation.Operation;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityMenuService;
import org.gvp.manager.pojo.SecurityMenu;
import java.util.List;

@RestController
@RequestMapping("/manager/admin/menu")
@RequiredArgsConstructor
public class SecurityMenuController implements BaseController<SecurityMenu> {
    private final SecurityMenuService securityMenuService;


    @Operation("获取全部一级菜单列表")
    @GetMapping("/list/first-level")
    public Result<List<SecurityMenu>> queryFirstLevel(){
        return Result.ok(this.securityMenuService.findFirstLevel());
    }

    @Operation("查找非子节点菜单列表")
    @GetMapping("/list/not-last")
    public Result<List<SecurityMenu>> queryNotLast() {
        return Result.ok(this.securityMenuService.findNotLast());
    }


    @PutMapping
    @Operation(value = "添加菜单信息数据",param = "菜单信息实体类对象")
    @Override
    public Result<Integer> add(@RequestBody SecurityMenu pojo) {
        return Result.ok(this.securityMenuService.save(pojo));
    }

    @PutMapping("/return")
    @Operation(value = "添加菜单信息数据,并反回新数据",param = "菜单信息实体类对象")
    @Override
    public Result<SecurityMenu> addAndReturn(@RequestBody SecurityMenu pojo) {
        return Result.ok(this.securityMenuService.saveAndReturn(pojo));
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(value = "删除菜单信息数据", param = "id: 菜单信息数据编号")
    @Override
    public Result<Integer> deleteById(@PathVariable Integer id) {
        return Result.ok(this.securityMenuService.removeById(id));
    }

    @PostMapping
    @Operation(value = "使用编号更新菜单信息数据", param = "pojo: 菜单信息数据,数据编号(ID)必须存在")
    @Override
    public Result<Integer> update(@RequestBody SecurityMenu pojo) {
        return Result.ok(this.securityMenuService.edit(pojo));
    }

    @PostMapping("/return")
    @Operation(value = "使用编号更新菜单信息数据,更新之后反回新的数据", param = "pojo: 菜单信息数据,数据编号(ID)必须存在")
    @Override
    public Result<SecurityMenu> updateAndReturn(@RequestBody SecurityMenu pojo) {
        return Result.ok(this.securityMenuService.editAndReturn(pojo));
    }

    @GetMapping("/{id:\\d+}")
    @Operation(value = "根据ID查找菜单信息数据", param = "id: 菜单信息数据ID")
    @Override
    public Result<SecurityMenu> queryById(@PathVariable Integer id) {
        return Result.ok(this.securityMenuService.findById(id));
    }

    @GetMapping("/list")
    @Operation("获取菜单信息数据列表")
    @Override
    public Result<List<SecurityMenu>> queryList() {
        return Result.ok(this.securityMenuService.findList());
    }

    @GetMapping("/limit/{start:\\d+}/{end:\\d+}")
    @Operation(value = "查找指定区间的菜单信息数据列表",param = "start: 起始位置, end: 结束位置")
    @Override
    public Result<List<SecurityMenu>> queryLimit(@PathVariable int start,@PathVariable int end) {
        return Result.ok(securityMenuService.findLimit(start, end));
    }

    @GetMapping("/count")
    @Operation("查找菜单信息全部数据列表")
    @Override
    public Result<Long> queryCount() {
        return Result.ok(this.securityMenuService.findCount());
    }

    @GetMapping("/page/{page:\\d+}/{size:\\d+}")
    @Operation(value = "分页查找菜单数据", param = "currentPage: 当前页, pageSize: 页大小")
    @Override
    public Result<Page<SecurityMenu>> queryPage(@PathVariable int page,@PathVariable int size) {
        return Result.ok(this.securityMenuService.findPage(page, size));
    }

}
