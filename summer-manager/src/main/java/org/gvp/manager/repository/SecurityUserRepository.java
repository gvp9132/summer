package org.gvp.manager.repository;

import org.gvp.common.constant.BaseRepository;
import org.gvp.manager.pojo.SecurityUser;
/**
 * 用户信息表 数据层接口
 */
public interface SecurityUserRepository extends BaseRepository<SecurityUser> {

    /**
     * 根据用户名查找用户信息
     */
    SecurityUser searchByUsername(String username);
}
