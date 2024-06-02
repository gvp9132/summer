package org.gvp.manager.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityRoleService;
import org.gvp.manager.repository.SecurityRoleRepository;
import org.gvp.manager.pojo.SecurityRole;
import java.util.List;
/**
 * 角色信息表 服务层接口实现类
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityRoleServiceImpl implements SecurityRoleService {
    private final SecurityRoleRepository securityRoleRepository;

    @Override
    public int save(SecurityRole pojo) {
        log.debug("添加角色信息表数据: {}",pojo);
        return this.securityRoleRepository.append(pojo);
    }

    @Override
    public SecurityRole saveAndReturn(SecurityRole pojo) {
        if (this.save(pojo) == 1){
            log.debug("添加并反回数据,执行添加成功");
            return this.findById(pojo.getId());
        }else {
            log.info("添加并反回数据,添加失败");
            return null;
        }
    }

    @Override
    public int removeById(Integer id) {
        log.debug("根据数据编号删除角色信息表数据: {}",id);
        return this.securityRoleRepository.cancel(id);
    }

    @Override
    public int edit(SecurityRole pojo) {
        log.debug("更新角色信息表数据,根据编号进行更新: {}",pojo);
        return this.securityRoleRepository.modify(pojo);
    }

    @Override
    public SecurityRole editAndReturn(SecurityRole pojo) {
        if (this.edit(pojo) == 1){
            log.debug("更新并反回数据执行更新成功");
            return this.findById(pojo.getId());
        }else {
            log.info("更新并反回数据执行更新失败");
            return null;
        }
    }

    @Override
    public SecurityRole findById(Integer id) {
        log.debug("使用角色信息表数据编号查找数据: {}",id);
        return this.securityRoleRepository.searchById(id);
    }

    @Override
    public List<SecurityRole> findList() {
        log.debug("查找全部角色信息表数据列表");
        return this.securityRoleRepository.searchList();
    }

    @Override
    public List<SecurityRole> findLimit(int start, int end) {
        log.debug("查找指定区间的角色信息表数据列表: {}-{}",start,end);
        return this.securityRoleRepository.searchLimit(start,end);
    }

    @Override
    public Long findCount() {
        log.debug("查找全部数据量");
        return securityRoleRepository.searchCount("");
    }

    @Override
    public Page<SecurityRole> findPage(int currentPage, int pageSize) {
        log.debug("查找角色信息表分页数据: 当前页={},页大小={}",currentPage,pageSize);
        return Page.<SecurityRole>builder()
                .total(this.findCount())
                .current(currentPage)
                .pageSize(pageSize)
                .build(this::findLimit);
    }

}
