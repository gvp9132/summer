package org.gvp.manager.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 前端使用代理处理icon,获取属性的时候修改图标显示
 */
public interface TreeData<T> extends Serializable {

    default void setId(Integer id){
    }
    default void setParentId(Integer parentId){
    }
    default Integer getId(){
        return null;
    }
    default Integer getParentId(){
        return null;
    }

    /**
     * 节点标题
     */
    public String getTitle();

    public String getExplain();
    public void setExplain(String explain);

    /**
     * 节点唯一标识
     */
    public String getKey();

    /**
     * 节点图标
     */
    public String getIcon();

    /**
     * 是否显示选择框
     */
    public boolean isCheckable();

    /**
     * 是否可以选中
     * @return
     */
    public boolean isSelectable();

    /**
     * 是否禁用节点
     */
    public boolean isDisabled();

    public void setTitle(String title) ;
    public void setKey(String key);

    public void setIcon(String icon);

    public void setCheckable(boolean checkable) ;

    public void setSelectable(boolean selectable);

    public void setDisabled(boolean disabled);

    /**
     * 子节点数据集合
     */
    public List<T> getChildren();
    public void setChildren(List<T> children);

}
