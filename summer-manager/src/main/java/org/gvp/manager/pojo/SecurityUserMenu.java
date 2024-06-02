package org.gvp.manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;
/**
* 用户菜单关系表实体类
*/
@Data
@TableName(schema = "summer",value = "security_user_menu")
public class SecurityUserMenu{
    /**
    * 自增主建
    */
    @TableId(type = IdType.AUTO,value = "`id`")
    private Integer id;
    /**
    * 此数据唯一识别码
    */
    @TableField(value = "`key`")
    private String key;
    /**
    * 用户id
    */
    @TableField(value = "`user_id`")
    private Integer userId;
    /**
    * 菜单id
    */
    @TableField(value = "`menu_id`")
    private Integer menuId;
    /**
    * 创建时间
    */
    @TableField(value = "`create_time`")
    private LocalDateTime createTime;
    /**
    * 数据版本
    */
    @TableField(value = "`version`")
    private Integer version;
    /**
    * 逻辑删除字段
    */
    @TableField(value = "`delete`")
    private Boolean delete;
    /**
    * 备注信息
    */
    @TableField(value = "`remark`")
    private String remark;
}
