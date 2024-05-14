package org.gvp.gateway.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 安全路径实体
 */
@Data
@Table(name = "security_path",schema = "summer")
public class SecurityPath {
    @Id
    private Integer id ;
    /**
     * 路径模式
     */
    private String pattern ;
    /**
     * 请求方法类型
     */
    private String method;
}
