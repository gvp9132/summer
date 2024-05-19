# data: 2024-5-14
# 未签名(无符号 UNSIGNED)INT类型最大值 4_294_967_295

# 用户表ID自曾: 1_100_000_001
# 角色表ID自曾: 1_200_000_001
# 菜单表ID自曾: 1_300_000_001
# 路径表ID自曾: 1_400_000_001

###########################################################################################
######################### SpringSecurity安全框架请求路径表 #################################
###########################################################################################

use summer ;
drop table if exists `security_path` ;
CREATE TABLE `security_path` (
                                 `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                                 `key` CHAR(32) NOT NULL UNIQUE COMMENT '数据唯一标识',
                                 `parent_id` INT UNSIGNED NOT NULL DEFAULT 1400000000 COMMENT '路径的父级路径编号',
                                 `pattern` VARCHAR(64) NOT NULL UNIQUE COMMENT '请求路径模式',
                                 `method` enum('GET','PUT','POST','DELETE','ANY','DEFAULT') NOT NULL DEFAULT 'ANY' COMMENT '请求方法',
                                 `explain` VARCHAR(64) NOT NULL COMMENT '请求路径说明',
                                 `server` VARCHAR(32) NOT NULL COMMENT '路径的归属微服务名字或说明',
                                 `last` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否是最后一级路径',
                                 `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间' ,
                                 `update_time` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间' ,
                                 `delete` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '逻辑删除字段' ,
                                 `version` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '数据版本' ,
                                 `order` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据排序字段' ,
                                 `remark` VARCHAR(255) COMMENT '数据备注信息'
) COMMENT '请求路径信息表' ENGINE = Innodb auto_increment=1400000001 default charset utf8mb4;

# CREATE UNIQUE INDEX authority_pattern ON `security_path`(`pattern`) comment '创建请求路径模式索引';
CREATE UNIQUE INDEX index_pattern_method ON `security_path`(`pattern`,`method`) comment '创建请求路径模式和方法联合索引';

# 注册role表的触发器,在添加数据的时候触发自动填充key字段使用32位uuid
create trigger summer.security_path_key_trigger
    before insert
    on summer.security_path
    for each row begin
    set new.`key` = REPLACE(UUID(),'-','');
end;

####################################################################################################
####################################################################################################
########################################## 请求路径约束  #############################################
####################################################################################################
####################################################################################################



