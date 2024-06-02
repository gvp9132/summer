package org.gvp.manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;
/**
* 菜单信息实体类
*/
@Data
@TableName(schema = "summer",value = "security_menu")
public class SecurityMenu{
    /**
    * 自增主键
    */
    @TableId(type = IdType.AUTO,value = "`id`")
    private Integer id;
    /**
    * 数据唯一标识
    */
    @TableField(value = "`key`")
    private String key;
    /**
    * 菜单的上一级菜单ID
    */
    @TableField(value = "`parent_id`")
    private Integer parentId;
    /**
    * 菜单标签(名字)
    */
    @TableField(value = "`label`")
    private String label;
    /**
    * 菜单功能描述
    */
    @TableField(value = "`describe`")
    private String describe;
    /**
    * 菜单路径地址
    */
    @TableField(value = "`path`")
    private String path;
    /**
    * 菜单图标
    */
    @TableField(value = "`icon`")
    private String icon;
    /**
    * 菜单是否是最后一级菜单
    */
    @TableField(value = "`last`")
    private Boolean last;
    /**
    * 菜单排序字段
    */
    @TableField(value = "`menu_order`")
    private Integer menuOrder;
    /**
    * 是否要显示菜单标志
    */
    @TableField(value = "`show`")
    private Boolean show;
    /**
    * 创建时间
    */
    @TableField(value = "`create_time`")
    private LocalDateTime createTime;
    /**
    * 修改时间
    */
    @TableField(value = "`update_time`")
    private LocalDateTime updateTime;
    /**
    * 逻辑删除字段
    */
    @TableField(value = "`delete`")
    private Boolean delete;
    /**
    * 数据版本
    */
    @TableField(value = "`version`")
    private Integer version;
    /**
    * 数据排序字段
    */
    @TableField(value = "`order`")
    private Integer order;
    /**
    * 数据备注信息
    */
    @TableField(value = "`remark`")
    private String remark;
}
