# data: 2024-5-14
# 未签名(无符号 UNSIGNED)INT类型最大值 4_294_967_295

# 用户表ID自曾: 1_100_000_001
# 角色表ID自曾: 1_200_000_001
# 菜单表ID自曾: 1_300_000_001
# 路径表ID自曾: 1_400_000_001

############################################################################################################
###############################################角色信息表#############################################################
############################################################################################################
use summer ;
drop table if exists `security_role` ;
CREATE TABLE `security_role` (
                                 `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                                 `key` CHAR(32) NOT NULL UNIQUE COMMENT '数据唯一标识',
                                 `authority` VARCHAR(32) NOT NULL UNIQUE COMMENT '角色定义',
                                 `icon` VARCHAR(32) NOT NULL COMMENT '角色图标',
                                 `explain` VARCHAR(32) NOT NULL COMMENT '角色说明',
                                 `weight` INT DEFAULT 1 COMMENT '角色权重',
                                 `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间' ,
                                 `update_time` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间' ,
                                 `delete` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '逻辑删除字段' ,
                                 `version` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '数据版本' ,
                                 `order` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据排序字段' ,
                                 `remark` VARCHAR(255) COMMENT '数据备注信息'
) COMMENT '角色信息表' ENGINE = Innodb auto_increment=1200000001 default charset utf8mb4;

CREATE UNIQUE INDEX key_index ON `security_role`(`key`) comment '创建角色表基于唯一标识的索引';
CREATE UNIQUE INDEX authority_index ON `security_role`(`authority`) comment '创建角色表基于角色定义的索引';

# 注册role表的触发器,在添加数据的时候触发自动填充key字段使用32位uuid
create trigger summer.security_role_key_trigger
    before insert
    on summer.security_role
    for each row begin
    set new.`key` = REPLACE(UUID(),'-','');
end;

