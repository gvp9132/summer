package org.gvp.manager.repository.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.gvp.manager.repository.SecurityRoleRepository;
import org.gvp.manager.mapper.SecurityRoleMapper;
import org.gvp.manager.pojo.SecurityRole;
import java.util.List;
/**
 * 角色信息表 数据层接口实现类
 */
@Repository
@RequiredArgsConstructor
public class SecurityRoleRepositoryImpl implements SecurityRoleRepository {
    private final SecurityRoleMapper securityRoleMapper;

    @Override
    public int append(SecurityRole pojo) {
        return this.securityRoleMapper.insert(pojo);
    }

    @Override
    public int modify(SecurityRole pojo) {
        return this.securityRoleMapper.updateById(pojo);
    }

    @Override
    public int cancel(Integer id) {
        return this.securityRoleMapper.deleteById(id);
    }

    @Override
    public SecurityRole searchById(Integer id) {
        return this.securityRoleMapper.selectById(id);
    }

    @Override
    public List<SecurityRole> searchList() {
        return this.securityRoleMapper.selectList(null);
    }

    @Override
    public Long searchCount(String field) {
        return this.securityRoleMapper.selectCount(null);
    }

    @Override
    public List<SecurityRole> searchLimit(int start, int end) {
        return this.securityRoleMapper.selectList(Wrappers.<SecurityRole>lambdaQuery()
                .last("LIMIT %d,%d".formatted(start,end))
                .orderByDesc(SecurityRole::getOrder)
                .orderByAsc(SecurityRole::getId)
        );
    }

}
