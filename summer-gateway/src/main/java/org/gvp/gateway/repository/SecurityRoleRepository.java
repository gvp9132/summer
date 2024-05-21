package org.gvp.gateway.repository;

import org.gvp.gateway.pojo.SecurityRole;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SecurityRoleRepository extends R2dbcRepository<SecurityRole, Integer> {

    @Query("""
    select r.`id` as `id`,`authority` from
        security_role r
            left outer join security_user_role ur on r.id = ur.role_id and ur.`delete` = false
        where ur.user_id = :userId
    """)
    Flux<SecurityRole> searchRolesByUserId(@Param("userId") Integer userId);

}
