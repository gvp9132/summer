package org.gvp.manager.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单权限树渲染数据
 */
@Data
public class MenuTreeData implements TreeData<MenuTreeData>{
    /**
     * 菜单ID
     */
    private Integer id;
    /**
     * 菜单父ID
     */
    private Integer parentId;
    /**
     * 菜单的路径地址(唯一标识)
     */
    private String key;
    /**
     * 权限树节点标题
     */
    private String title;
    /**
     * 权限树节点说明
     */
    private String explain;
    /**
     * icon
     */
    private String icon;
    /**
     * 是否显示选择框
     */
    private boolean checkable = true;
    /**
     * 是否可以选中
     */
    private boolean selectable;
    /**
     * 是否禁用节点
     */
    private boolean disabled;
    /**
     * 子菜单
     */
    private List<MenuTreeData> children;
}
