package org.gvp.manager.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.gvp.manager.dto.RoleTreeData;
import org.gvp.manager.to.MapperUpdate;
import org.springframework.stereotype.Service;
import org.gvp.manager.service.SecurityUserRoleService;
import org.gvp.manager.repository.SecurityUserRoleRepository;
import org.gvp.manager.pojo.SecurityUserRole;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户角色关系表 服务层接口实现类
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityUserRoleServiceImpl implements SecurityUserRoleService {
    private final SecurityUserRoleRepository securityUserRoleRepository;


    @Override
    public Integer editAuthority(AuthorityTreeUpdate update) {
        log.debug("更新用户角色关系: {}",update);
        return this.saveUserAuthority(update.getId(),update.getAddNodes()) +
                this.removeUserAuthority(update.getId(),update.getDeleteNodes());
    }

    /**
     * 添加用户角色权限,添加之前需要确认数据是否存在
     */
    private int saveUserAuthority(Integer userId,List<String> roleKeys){
        if (roleKeys == null || roleKeys.isEmpty()){
            return 0;
        }
        AtomicInteger res = new AtomicInteger(0);
        List<Integer> ids = this.securityUserRoleRepository.searchRoleIds(roleKeys);
        log.debug("根据角色keys查找ids: {},{}",roleKeys,ids);
        ids.forEach(roleId -> {
            SecurityUserRole ur = this.findByUserIdAndRoleId(userId, roleId);
            if (ur == null){
                ur = new SecurityUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                res.addAndGet(this.save(ur));
            }else {
                res.addAndGet(this.recoverRemove(ur.getId()));
            }
        });
        return res.get();
    }

    @Override
    public SecurityUserRole findByUserIdAndRoleId(Integer userId, Integer roleId) {
        log.debug("根据用户id和角色id查找用户角色关系数据: {}-{}",userId,roleId);
        return this.securityUserRoleRepository.searchByUserIdAndRoleId(userId,roleId);
    }

    /**
     * 删除权限
     */
    private int removeUserAuthority(Integer userId,List<String> roleKeys){
        if (roleKeys == null || roleKeys.isEmpty()){
            return 0;
        }
        return this.logicalRemove(new MapperUpdate(userId,roleKeys));
    }

    /**
     * 逻辑删除
     */
    private int logicalRemove(MapperUpdate update) {
        log.debug("根据用户的ID和角色的KYE集合删除删除用户和角色关系: {}", update);
        return this.securityUserRoleRepository.logicalCancel(update);
    }

    @Override
    public int recoverRemove(Integer id) {
        log.debug("根据数据id恢复删除的用户角色关系: {}",id);
        return this.securityUserRoleRepository.recoverCancel(id);
    }

    @Override
    public AuthorityTreeData findAuthorityTreeData(Integer userId) {
        log.debug("根据用户编号获取用户角色处理权限树: {}",userId);
        List<RoleTreeData> roleTreeData = this.findTreeData();
        AuthorityTreeData authorityTreeData = new AuthorityTreeData();
        authorityTreeData.setTreeData(roleTreeData);
        List<String> userRoleByUserId = this.findUserRoleByUserId(userId);
        authorityTreeData.setAuthorityKeys(userRoleByUserId);
        return authorityTreeData;
    }

    @Override
    public List<RoleTreeData> findTreeData() {
        log.debug("获取角色渲染树数据");
        return this.securityUserRoleRepository.searchTreeData();
    }

    @Override
    public List<String> findUserRoleByUserId(Integer userId){
        log.debug("获取用户的角色key列表: {}",userId);
        return this.securityUserRoleRepository.searchUserRoleByUserId(userId);
    }
    @Override
    public int save(SecurityUserRole pojo) {
        log.debug("添加用户角色关系表数据: {}",pojo);
        return this.securityUserRoleRepository.append(pojo);
    }

}
