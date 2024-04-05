package org.gvp.gateway.repository;

import org.gvp.gateway.pojo.SecurityUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * 用户数据操作接口
 */
@Repository
public interface SecurityUserRepository extends R2dbcRepository<SecurityUser,Integer> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    Mono<SecurityUser> searchByUsername(@Param("username") String username);
}
