# data: 2024-5-17
# 未签名(无符号 UNSIGNED)INT类型最大值 4_294_967_295

# 用户表ID自曾: 1_100_000_001
# 角色表ID自曾: 1_200_000_001
# 菜单表ID自曾: 1_300_000_001
# 路径表ID自曾: 1_400_000_001

# 用户角色表ID自曾:    2_100_000_001
# 角色请求路径表ID自曾: 2_200_000_001

#################################################################################################################
############################################### Spring安全框架 角色-请求路径关系表 #######################################
#################################################################################################################

use summer ;
drop table if exists `security_role_path`;
create table `security_role_path`
(
    `id`          int UNSIGNED primary key auto_increment comment '自增主建',
    `key`         char(32) not null comment '此数据唯一识别码',
    `role_id`     int         not null comment '角色编号',
    `path_id`     int         not null comment '路径编号',
    `version`     int         not null default 1 comment '数据版本' ,
    `delete`      boolean     not null default false comment '逻辑删除字段',
    `remark`      varchar(64) comment '备注信息'
) comment '角色路径关系表' ENGINE = Innodb
                           auto_increment = 2200000001
                           default charset utf8mb4;
CREATE UNIQUE INDEX user_role_id_index ON `security_role_path`(`role_id`,`path_id`) comment '创建角色编号和路径编号的索引';
create trigger summer._role_path_key_trigger
    before insert
    on summer.security_role_path
    for each row begin
    set new.`key` = REPLACE(UUID(),'-','');
end;
