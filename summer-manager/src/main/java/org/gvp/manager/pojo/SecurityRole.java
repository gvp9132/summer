package org.gvp.manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;
/**
* 角色信息表实体类
*/
@Data
@TableName(schema = "summer",value = "security_role")
public class SecurityRole{
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
    * 角色定义
    */
    @TableField(value = "`authority`")
    private String authority;
    /**
    * 角色图标
    */
    @TableField(value = "`icon`")
    private String icon;
    /**
    * 角色说明
    */
    @TableField(value = "`explain`")
    private String explain;
    /**
    * 角色权重
    */
    @TableField(value = "`weight`")
    private Integer weight;
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
