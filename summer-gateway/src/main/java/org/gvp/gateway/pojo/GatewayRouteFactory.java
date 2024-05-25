package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 网关路由工厂信息实体类
 */
@Data
@Table(name = "gateway_route_factory",schema = "summer")
public class GatewayRouteFactory {
    /**
     * 主键ID数据编号
     */
    @Id
    private Integer id;
    /**
     * 数据唯一标识
     */
    private String kye;
    /**
     * 路由工厂名字
     */
    private String name;
    /**
     * 网关路由信息实体类的数据编号ID,用户确认工厂信息归属
     */
    private Integer routeId;
    /**
     * 路由工厂类型: predicate为路由断言工厂,filter为路由过滤工厂
     */
    private String type;
    /**
     * 路由工厂的参数信息,最终形式为LinkedHashMap<>(),map的key为_genkey_ + 数字
     */
    private String args;
    /**
     * 路由元数据
     */
    private String metadata;
    /**
     * 数据删除标识
     */
    private Boolean delete;
    /**
     * 数据版本(乐观锁)
     */
    private Integer version;
    /**
     * 路由备注信息
     */
    private String remark;

}
