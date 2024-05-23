package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;


/**
 * 网关路由工厂信息
 */
@Data
@Table(name = "gateway_route_factory",schema = "summer")
public class GatewayRouteFactory implements Serializable {



    /**
     * 主键(数据)id
     */
    @Id
    private Integer id ;
    /**
     * 路由id,对应gateway的路由id,也用户来对路由的谓词和过滤器进行绑定
     */
    private String routeId ;
    /**
     * 路由工厂类型predicate:断言工厂,filter:过滤器工厂
     */
    private String type;
    /**
     * 断言(谓词工厂)的名字
     */
    private String name ;
    /**
     * 断言的参数,最终转换为LinkedHashMap<String, String>,存储时候使用json
     */
    private String args ;
    /**
     * 备注说明信息
     */
    private String remark;
}
