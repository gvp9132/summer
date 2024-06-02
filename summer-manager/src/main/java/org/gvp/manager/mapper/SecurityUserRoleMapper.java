package org.gvp.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.gvp.manager.dto.RoleTreeData;
import org.gvp.manager.pojo.SecurityUserRole;
import org.gvp.manager.to.MapperUpdate;

import java.util.List;

/**
* 用户角色关系表 mp映射接口
*/
@Mapper
public interface SecurityUserRoleMapper extends BaseMapper<SecurityUserRole> {

    /**
     * 根据角色的key集合查找角色的id集合
     */
    List<Integer> selectRoleIds(List<String> keys);

    /**
     * 获取角色渲染树数据
     */
    List<RoleTreeData> selectTreeData();

    /**
     * 获取用户授权的角色key列表
     */
    List<String> selectUserRoleByUserId(Integer userId);

    /**
     * 根据用户的ID和角色的KYE集合删除删除用户和角色关系
     * @param update 更新映射类
     */
    int logicallyDelete(MapperUpdate update);

    /**
     * 根据用户编号和角色编号恢复删除的用户角色关系
     * @param id 用户角色关系表的ID
     */
    int logicalRecovery(Integer id);

    SecurityUserRole selectByUserIdAndRoleId(Integer userId, Integer roleId);
}
