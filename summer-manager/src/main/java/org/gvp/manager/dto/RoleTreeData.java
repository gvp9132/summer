package org.gvp.manager.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色权限树渲染数据
 */
@Data
public class RoleTreeData implements TreeData<RoleTreeData>{
    private Integer id;
    private String title;
    private String explain;
    private String key;
    private String icon;
    private boolean checkable = true;
    private boolean selectable;
    private boolean disabled;
    private List<RoleTreeData> children;
}
