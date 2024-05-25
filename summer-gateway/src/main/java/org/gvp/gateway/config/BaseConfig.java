package org.gvp.gateway.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.pattern.PathPatternParser;

@Log4j2
@Configuration
public class BaseConfig {
    /**
     * 路径匹配器
     */
    @Bean
    public PathPatternParser pathPatternParser(){
        return PathPatternParser.defaultInstance;
    }

}

