package org.gvp.manager.dto;

import lombok.Data;

import java.util.List;

/**
 * 权限分配树渲染数据
 */
@Data
public class AuthorityTreeData {
    private List<? extends TreeData> treeData;
    /**
     * 默认选中的树节点key数组
     */
    private List<String> authorityKeys ;
    /**
     * 默认展开的树节点key数组
     */
    private List<String> expandKeys;

    public AuthorityTreeData() {
    }

    public AuthorityTreeData(List<? extends TreeData> treeData, List<String> authorityKeys, List<String> expandKeys) {
        this.treeData = treeData;
        this.authorityKeys = authorityKeys;
        this.expandKeys = expandKeys;
    }
}
