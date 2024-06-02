package org.gvp.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 前端导航菜单数据
 */
@Data
public class NavigateMenuData implements Serializable {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer parentId;
    private String key;
    /**
     * 菜单标题
     */
    private String label;
    /**
     * 菜单收缩时展示的悬浮标题
     */
    private String title;
    private String icon;
    /**
     * 菜单禁用
     */
    private String disabled;
    private List<NavigateMenuData> children;
}
