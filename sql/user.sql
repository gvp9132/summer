# data: 2024-4-4
# 未签名(无符号 UNSIGNED)INT类型最大值 4_294_967_295

# 用户表ID自曾: 1_100_000_001
# 角色表ID自曾: 1_200_000_001
# 菜单表ID自曾: 1_300_000_001
# 路径表ID自曾: 1_400_000_001

############################################################################################################
###############################################用户信息表#############################################################
############################################################################################################
USE summer;
DROP TABLE IF EXISTS `security_user` ;
CREATE TABLE `security_user` (
                                 `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                                 `key` CHAR(32) NOT NULL UNIQUE COMMENT '数据唯一标识',
                                 `username` VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
                                 `password` VARCHAR(68) NOT NULL COMMENT '用密码',
                                 `identity` VARCHAR(16) COMMENT '用户身份',
                                 `age` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '用户年龄',
                                 `gender` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '用性别',
                                 `enabled`                 boolean not null default true comment '用户是否启用',
                                 `account_non_locked`      boolean not null default true comment '用户是否锁定',
                                 `account_non_expired`     boolean not null default true comment '用户是否过期',
                                 `credentials_non_expired` boolean not null default true comment '用户凭证是否过期',
                                 `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间' ,
                                 `update_time` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间' ,
                                 `delete` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '逻辑删除字段' ,
                                 `version` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '数据版本' ,
                                 `order` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据排序字段' ,
                                 `remark` VARCHAR(255) COMMENT '数据备注信息'
) COMMENT '用户信息表' ENGINE = Innodb auto_increment=1100000001 default charset utf8mb4;

CREATE UNIQUE INDEX username_index ON `security_user`(`username`) comment '用户名唯一索引';

DROP TRIGGER IF EXISTS summer.security_user_key_trigger;
CREATE TRIGGER summer.security_user_key_trigger
    BEFORE INSERT
    ON summer.security_user
    FOR EACH ROW BEGIN
    SET new.`key` = REPLACE(UUID(),'-','');
END;