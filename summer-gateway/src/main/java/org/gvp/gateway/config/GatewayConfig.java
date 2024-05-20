package org.gvp.gateway.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.util.pattern.PathPatternParser;

@Log4j2
@Configuration
@EnableConfigurationProperties
public class GatewayConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @PostConstruct
    public void init() {
        log.debug("网关安全模块配置文件: {}", securityProperties);
//        Mono<Boolean> test = this.reactiveRedisTemplate.hasKey("test");
//        test.subscribe( e -> log.error("测试key[test],如果不测试会报错连接redis失败: {}",e) );

    }


    /**
     * 路径匹配器
     */
    @Bean
    public PathPatternParser pathPatternParser(){
        return PathPatternParser.defaultInstance;
    }
}
