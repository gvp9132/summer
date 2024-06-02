package org.gvp.manager.service;

import org.gvp.common.constant.BaseService;
import org.gvp.manager.pojo.SecurityUser;
/**
 * 用户信息表 数据层接口
 */
public interface SecurityUserService extends BaseService<SecurityUser> {

    /**
     * 根据用户名查找用户信息
     */
    SecurityUser findByUsername(String username);
}
