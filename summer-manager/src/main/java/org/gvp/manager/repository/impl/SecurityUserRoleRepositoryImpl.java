package org.gvp.manager.repository.impl;

import lombok.RequiredArgsConstructor;

import org.gvp.manager.dto.RoleTreeData;
import org.gvp.manager.to.MapperUpdate;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.gvp.manager.repository.SecurityUserRoleRepository;
import org.gvp.manager.mapper.SecurityUserRoleMapper;
import org.gvp.manager.pojo.SecurityUserRole;
import java.util.List;
/**
 * 用户角色关系表 数据层接口实现类
 */
@Repository
@RequiredArgsConstructor
public class SecurityUserRoleRepositoryImpl implements SecurityUserRoleRepository {
    private final SecurityUserRoleMapper securityUserRoleMapper;

    @Override
    public SecurityUserRole searchByUserIdAndRoleId(Integer userId, Integer roleId) {
        return this.securityUserRoleMapper.selectByUserIdAndRoleId(userId,roleId);
    }

    @Override
    public List<Integer> searchRoleIds(List<String> roleKeys) {
        return this.securityUserRoleMapper.selectRoleIds(roleKeys);
    }

    @Override
    public int logicalCancel(MapperUpdate update) {
        return this.securityUserRoleMapper.logicallyDelete(update);
    }

    @Override
    public int recoverCancel(Integer id) {
        return this.securityUserRoleMapper.logicalRecovery(id);
    }

    @Override
    public List<RoleTreeData> searchTreeData() {
        return this.securityUserRoleMapper.selectTreeData();
    }

    @Override
    public List<String> searchUserRoleByUserId(Integer userId) {
        return this.securityUserRoleMapper.selectUserRoleByUserId(userId);
    }

    @Override
    public int append(SecurityUserRole pojo) {
        return this.securityUserRoleMapper.insert(pojo);
    }

    @Override
    public int modify(SecurityUserRole pojo) {
        return this.securityUserRoleMapper.updateById(pojo);
    }

    @Override
    public int cancel(Integer id) {
        return this.securityUserRoleMapper.deleteById(id);
    }

    @Override
    public SecurityUserRole searchById(Integer id) {
        return this.securityUserRoleMapper.selectById(id);
    }

    @Override
    public List<SecurityUserRole> searchList() {
        return this.securityUserRoleMapper.selectList(null);
    }

    @Override
    public Long searchCount(String field) {
        return this.securityUserRoleMapper.selectCount(null);
    }

    @Override
    public List<SecurityUserRole> searchLimit(int start, int end) {
        return this.securityUserRoleMapper.selectList(Wrappers.<SecurityUserRole>lambdaQuery()
                .last("LIMIT %d,%d".formatted(start,end))
                .orderByAsc(SecurityUserRole::getId)
        );
    }

}
