package org.gvp.manager.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityUserService;
import org.gvp.manager.repository.SecurityUserRepository;
import org.gvp.manager.pojo.SecurityUser;
import java.util.List;
/**
 * 用户信息表 服务层接口实现类
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
    private final SecurityUserRepository securityUserRepository;

    @Override
    public SecurityUser findByUsername(String username) {
        log.debug("根据用户名查找用户信息: {}",username);
        return this.securityUserRepository.searchByUsername(username);
    }

    @Override
    public int save(SecurityUser pojo) {
        log.debug("添加用户信息表数据: {}",pojo);
        return this.securityUserRepository.append(pojo);
    }

    @Override
    public SecurityUser saveAndReturn(SecurityUser pojo) {
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
        log.debug("根据数据编号删除用户信息表数据: {}",id);
        return this.securityUserRepository.cancel(id);
    }

    @Override
    public int edit(SecurityUser pojo) {
        log.debug("更新用户信息表数据,根据编号进行更新: {}",pojo);
        return this.securityUserRepository.modify(pojo);
    }

    @Override
    public SecurityUser editAndReturn(SecurityUser pojo) {
        if (this.edit(pojo) == 1){
            log.debug("更新并反回数据执行更新成功");
            return this.findById(pojo.getId());
        }else {
            log.info("更新并反回数据执行更新失败");
            return null;
        }
    }

    @Override
    public SecurityUser findById(Integer id) {
        log.debug("使用用户信息表数据编号查找数据: {}",id);
        return this.securityUserRepository.searchById(id);
    }

    @Override
    public List<SecurityUser> findList() {
        log.debug("查找全部用户信息表数据列表");
        return this.securityUserRepository.searchList();
    }

    @Override
    public List<SecurityUser> findLimit(int start, int end) {
        log.debug("查找指定区间的用户信息表数据列表: {}-{}",start,end);
        return this.securityUserRepository.searchLimit(start,end);
    }

    @Override
    public Long findCount() {
        log.debug("查找全部数据量");
        return securityUserRepository.searchCount("");
    }

    @Override
    public Page<SecurityUser> findPage(int currentPage, int pageSize) {
        log.debug("查找用户信息表分页数据: 当前页={},页大小={}",currentPage,pageSize);
        return Page.<SecurityUser>builder()
                .total(this.findCount())
                .current(currentPage)
                .pageSize(pageSize)
                .build(this::findLimit);
    }

}
