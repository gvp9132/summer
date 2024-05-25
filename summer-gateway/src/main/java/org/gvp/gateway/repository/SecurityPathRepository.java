package org.gvp.gateway.repository;

import org.gvp.gateway.pojo.SecurityPath;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 路径数据操作接口
 * @data 2024-5-18
 */
@Repository
public interface SecurityPathRepository extends R2dbcRepository<SecurityPath,Integer> {

    /**
     * 根据角色名集合查询路径信息
     */
    @Query("""
    select p.`id`, p.pattern, p.method
    from security_path p
             left join security_role_path rp on p.id = rp.path_id
    where p.`delete` = false
      and rp.`delete` = false
      and rp.role_id in (select id from security_role where authority in (:roleNames))
    """)
    Flux<SecurityPath> searchByRoleNames(@Param("roleNames") List<String> roleNames);

    /**
     * 根据权限名字查询权限可用路径信息
     * @param authority
     * @return
     */
    @Query("""
    select p.`id`, p.pattern, p.method
    from security_path p
             left join security_role_path rp on p.id = rp.path_id
    where p.`delete` = false
      and rp.`delete` = false
      and rp.role_id in (select id from security_role where authority = :authority)
    """)
    Flux<SecurityPath> searchByAuthority(@Param("authority") String authority);

}
