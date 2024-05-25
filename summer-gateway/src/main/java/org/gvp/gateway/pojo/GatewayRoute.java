package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 网关路由信息实体类
 */
@Data
@Table(name = "gateway_route",schema = "summer")
public class GatewayRoute {
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
     * 路由ID,对应gateway的路由ID
     */
    private String routeId;
    /**
     * 路由url地址
     */
    private String uri;
    /**
     * 路由排序
     */
    private Integer order;
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
