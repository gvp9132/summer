# data: 2024-5-23
# 未签名(无符号 UNSIGNED)INT类型最大值 4_294_967_295
# 日期时间类型性能: bigint > datetime > timestamp

# 路由信息表ID自增:     901_000_001
# 路由断言工厂表ID自增:  902_000_001
# 路由过滤器表ID自增:   903_000_001

USE summer;
############################################################################################################
######################################## Gateway 网关路由信息表 ###############################################
############################################################################################################

DROP TABLE IF EXISTS gateway_route;
CREATE TABLE gateway_route(
                              `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                              `key` CHAR(32) NOT NULL UNIQUE COMMENT '数据唯一标识',
                              `route_id` VARCHAR(32) NOT NULL UNIQUE COMMENT '路由ID',
                              `uri` VARCHAR(255) NOT NULL COMMENT '路由URI地址',
                              `order` INT NOT NULL DEFAULT 0 COMMENT '路由排序',
                              `metadata` JSON COMMENT '路由元数据,额外存储的信息',
                              `delete` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '逻辑删除字段' ,
                              `version` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '数据版本' ,
                              `remark` VARCHAR(255) COMMENT '数据备注信息'
)COMMENT '网关路由信息表' ENGINE = Innodb auto_increment=901000001 default charset utf8mb4;

DROP TRIGGER IF EXISTS summer.gateway_route_key_trigger;
CREATE TRIGGER summer.gateway_route_key_trigger
    BEFORE INSERT
    ON summer.gateway_route
    FOR EACH ROW BEGIN
    SET new.`key` = REPLACE(UUID(),'-','');
END;

insert into gateway_route(route_id,uri,metadata,remark) value ('summer-manager','lb://summer-manager','{}','后台管理路由全部接口');


############################################################################################################
######################################## Gateway 网关路由工厂信息表 #######################################
############################################################################################################

DROP TABLE IF EXISTS gateway_route_factory;
CREATE TABLE gateway_route_factory(
                              `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                              `key` CHAR(32) NOT NULL UNIQUE COMMENT '数据唯一标识',
                              `route_id` VARCHAR(32) NOT NULL UNIQUE COMMENT '路由ID',
                              `type` ENUM('predicate','filter') NOT NULL DEFAULT 'predicate' COMMENT '路由断言工厂类型,predicate:断言工厂,filters:过滤器工厂',
                              `name` VARCHAR(32) NOT NULL COMMENT '路由断言工厂名字',
                              `args` JSON COMMENT '路由工厂参数',
                              `metadata` JSON COMMENT '路由元数据,额外存储的信息',
                              `delete` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '逻辑删除字段' ,
                              `version` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '数据版本' ,
                              `remark` VARCHAR(255) COMMENT '数据备注信息'
)COMMENT '网关路由工厂信息表' ENGINE = Innodb auto_increment=902000001 default charset utf8mb4;

DROP TRIGGER IF EXISTS summer.gateway_route_factory_key_trigger;
CREATE TRIGGER summer.gateway_route_factory_key_trigger
    BEFORE INSERT
    ON summer.gateway_route_factory
    FOR EACH ROW BEGIN
    SET new.`key` = REPLACE(UUID(),'-','');
END;




insert into gateway_route_factory(route_id,type,name,args,metadata,remark)
    value ('summer-manager','predicate','Path','{}','{"_genkey_1":"/manager/**"}','后台管理路由路径断言工厂');