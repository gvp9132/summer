package org.gvp.manager.service;

import org.gvp.common.constant.BaseService;
import org.gvp.manager.dto.AuthorityTreeData;
import org.gvp.manager.dto.AuthorityTreeUpdate;
import org.gvp.manager.dto.RoleTreeData;
import org.gvp.manager.pojo.SecurityUserRole;

import java.util.List;

/**
 * 用户角色关系表 服务层接口
 */
public interface SecurityUserRoleService extends BaseService<SecurityUserRole> {

    /**
     * 根据用户编号获取用户角色处理权限树
     * @param userId 用户编号
     */
    AuthorityTreeData findAuthorityTreeData(Integer userId);

    /**
     * 获取角色渲染树数据
     */
    List<RoleTreeData> findTreeData();

    /**
     * 获取用户授权的角色key列表
     * @param userId 用户id
     */
    List<String> findUserRoleByUserId(Integer userId);

    /**
     * 更新用户角色关系
     */
    Integer editAuthority(AuthorityTreeUpdate update);

    /**
     * 根据用户id和角色id查找用户角色关系数据
     */
    SecurityUserRole findByUserIdAndRoleId(Integer userId, Integer roleId);
}

