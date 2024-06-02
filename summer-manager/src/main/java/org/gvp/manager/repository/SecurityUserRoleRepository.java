package org.gvp.manager.repository;

import org.gvp.common.constant.BaseRepository;
import org.gvp.manager.dto.RoleTreeData;
import org.gvp.manager.pojo.SecurityUserRole;
import org.gvp.manager.to.MapperUpdate;

import java.util.List;

/**
 * 用户角色关系表 数据层接口
 */
public interface SecurityUserRoleRepository extends BaseRepository<SecurityUserRole> {

    /**
     * 获取角色渲染树数据
     */
    List<RoleTreeData> searchTreeData();

    /**
     * 获取用户授权的角色key列表
     */
    List<String> searchUserRoleByUserId(Integer userId);

    /**
     * 根据用户的ID和角色的KYE集合删除删除用户和角色关系
     * @param update 更新映射类
     */
    int logicalCancel(MapperUpdate update);

    /**
     * 根据角色的key集合查找角色的id集合
     */
    List<Integer> searchRoleIds(List<String> roleKeys);

    /**
     * 根据用户id和角色id查找用户角色关系信息
     */
    SecurityUserRole searchByUserIdAndRoleId(Integer userId, Integer roleId);
}
