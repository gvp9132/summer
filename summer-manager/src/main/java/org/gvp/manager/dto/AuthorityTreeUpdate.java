package org.gvp.manager.dto;

import lombok.Data;

import java.util.List;

/**
 * 权限树更新类型参数类
 */
@Data
public class AuthorityTreeUpdate {
    /**
     * 更新主体的数据编号,如果更新用户角色关系,则为用户编号
     */
    private Integer id;
    /**
     * 需要添加的权限key集合
     */
    private List<String> addNodes;

    /**
     * 需要删除的权限key集合
     */
    private List<String> deleteNodes;
}
