package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类只是作为转换用
 */
@Data
@Table(name = "gateway_route",schema = "summer")
public class GatewayRoute {
    /**
     * 主键(数据)id,此字段只是作为数据的唯一标识
     */
    @Id
    private Integer id ;
    /**
     * 此字段作为数据的唯一标识
     */
    private String key;
    /**
     * 路由id,对应gateway的路由(RouteDefinition)id,也用户来对路由的谓词和过滤器进行绑定
     */
    private String routeId ;
    /**
     * 路由应用到的地址,对应gateway的路由uri
     */
    private String uri ;
    /**
     * 路由的顺序,对应gateway的路由order
     */
    private Integer order ;
    /**
     * 路由的元数据,对应gateway的路由metadata,用于存储路由的一些额外信息,最终需要换成HashMap,使用json在数据库中存储
     */
    private String metadata ;
    /**
     * 备注说明信息
     */
    private String remark;

    /**
     * 路由断言工厂
     */
    private List<GatewayRouteFactory> factories = new ArrayList<>();



//    private List<FilterDefinition> filters = new ArrayList<>();


}
