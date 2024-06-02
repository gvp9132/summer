package org.gvp.manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;
/**
* 用户信息表实体类
*/
@Data
@TableName(schema = "summer",value = "security_user")
public class SecurityUser{
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
    * 用户名
    */
    @TableField(value = "`username`")
    private String username;
    /**
    * 用密码
    */
    @TableField(value = "`password`")
    private String password;
    /**
    * 用户身份
    */
    @TableField(value = "`identity`")
    private String identity;
    /**
    * 用户年龄
    */
    @TableField(value = "`age`")
    private Integer age;
    /**
    * 用性别
    */
    @TableField(value = "`gender`")
    private Boolean gender;
    /**
    * 用户是否启用
    */
    @TableField(value = "`enabled`")
    private Boolean enabled;
    /**
    * 用户是否锁定
    */
    @TableField(value = "`account_non_locked`")
    private Boolean accountNonLocked;
    /**
    * 用户是否过期
    */
    @TableField(value = "`account_non_expired`")
    private Boolean accountNonExpired;
    /**
    * 用户凭证是否过期
    */
    @TableField(value = "`credentials_non_expired`")
    private Boolean credentialsNonExpired;
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
