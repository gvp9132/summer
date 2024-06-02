package org.gvp.manager.repository.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.gvp.manager.repository.SecurityUserRepository;
import org.gvp.manager.mapper.SecurityUserMapper;
import org.gvp.manager.pojo.SecurityUser;
import java.util.List;
/**
 * 用户信息表 数据层接口实现类
 */
@Repository
@RequiredArgsConstructor
public class SecurityUserRepositoryImpl implements SecurityUserRepository {
    private final SecurityUserMapper securityUserMapper;

    @Override
    public SecurityUser searchByUsername(String username) {
        return this.securityUserMapper
                .selectOne(Wrappers.<SecurityUser>lambdaQuery()
                        .select(SecurityUser::getId)
                        .eq(SecurityUser::getUsername, username));
    }

    @Override
    public int append(SecurityUser pojo) {
        return this.securityUserMapper.insert(pojo);
    }

    @Override
    public int modify(SecurityUser pojo) {
        return this.securityUserMapper.updateById(pojo);
    }

    @Override
    public int cancel(Integer id) {
        return this.securityUserMapper.deleteById(id);
    }

    @Override
    public SecurityUser searchById(Integer id) {
        return this.securityUserMapper.selectById(id);
    }

    @Override
    public List<SecurityUser> searchList() {
        return this.securityUserMapper.selectList(null);
    }

    @Override
    public Long searchCount(String field) {
        return this.securityUserMapper.selectCount(null);
    }

    @Override
    public List<SecurityUser> searchLimit(int start, int end) {
        return this.securityUserMapper.selectList(Wrappers.<SecurityUser>lambdaQuery()
                .last("LIMIT %d,%d".formatted(start,end))
                .orderByDesc(SecurityUser::getOrder)
                .orderByAsc(SecurityUser::getId)
        );
    }

}
