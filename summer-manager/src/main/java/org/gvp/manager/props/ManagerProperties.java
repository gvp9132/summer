package org.gvp.manager.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "summer.manager.config")
public class ManagerProperties{

    /**
     * 递归最大深度
     */
    private Integer recursionDepth ;

}
