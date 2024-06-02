package org.gvp.manager.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.gvp.common.http.Page;
import org.gvp.manager.service.SecurityMenuService;
import org.gvp.manager.repository.SecurityMenuRepository;
import org.gvp.manager.pojo.SecurityMenu;
import java.util.List;
/**
 * 菜单信息 服务层接口实现类
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityMenuServiceImpl implements SecurityMenuService {
    private final SecurityMenuRepository securityMenuRepository;


    @Override
    public List<SecurityMenu> findNotLast() {
        log.debug("查找非子节点菜单,last=false");
        return this.securityMenuRepository.searchNotLase();
    }

    @Override
    public List<SecurityMenu> findFirstLevel() {
        log.debug("查找全部一级菜单列表");
        return this.securityMenuRepository.searchFirstLevel();
    }

    @Override
    public int save(SecurityMenu pojo) {
        log.debug("添加菜单信息数据: {}",pojo);
        return this.securityMenuRepository.append(pojo);
    }

    @Override
    public SecurityMenu saveAndReturn(SecurityMenu pojo) {
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
        log.debug("根据数据编号删除菜单信息数据: {}",id);
        return this.securityMenuRepository.cancel(id);
    }

    @Override
    public int edit(SecurityMenu pojo) {
        log.debug("更新菜单信息数据,根据编号进行更新: {}",pojo);
        return this.securityMenuRepository.modify(pojo);
    }

    @Override
    public SecurityMenu editAndReturn(SecurityMenu pojo) {
        if (this.edit(pojo) == 1){
            log.debug("更新并反回数据执行更新成功");
            return this.findById(pojo.getId());
        }else {
            log.info("更新并反回数据执行更新失败");
            return null;
        }
    }

    @Override
    public SecurityMenu findById(Integer id) {
        log.debug("使用菜单信息数据编号查找数据: {}",id);
        return this.securityMenuRepository.searchById(id);
    }

    @Override
    public List<SecurityMenu> findList() {
        log.debug("查找全部菜单信息数据列表");
        return this.securityMenuRepository.searchList();
    }

    @Override
    public List<SecurityMenu> findLimit(int start, int end) {
        log.debug("查找指定区间的菜单信息数据列表: {}-{}",start,end);
        return this.securityMenuRepository.searchLimit(start,end);
    }

    @Override
    public Long findCount() {
        log.debug("查找全部数据量");
        return securityMenuRepository.searchCount("");
    }

    @Override
    public Page<SecurityMenu> findPage(int currentPage, int pageSize) {
        log.debug("查找菜单信息分页数据: 当前页={},页大小={}",currentPage,pageSize);
        return Page.<SecurityMenu>builder()
                .total(this.findCount())
                .current(currentPage)
                .pageSize(pageSize)
                .build(this::findLimit);
    }

}
