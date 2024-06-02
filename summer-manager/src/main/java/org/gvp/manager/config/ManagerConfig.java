package org.gvp.manager.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

import java.time.Duration;

@Configuration
public class ManagerConfig {

    /**
     * 自定义lettuce客户端配置,启用保活配置
     * 线上项目使用 lettuce 客户端，当操作 redis 得接口一段时间没有调用后（比如30分钟），再次调用 redis 操作后，就会遇到连接超时得问题，导致接口异常
     */
    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return clientConfigurationBuilder -> {
            LettuceClientConfiguration clientConfiguration = clientConfigurationBuilder.build();
            ClientOptions clientOptions = clientConfiguration.getClientOptions().orElseGet(ClientOptions::create);
            ClientOptions build = clientOptions.mutate().build();
            SocketOptions.KeepAliveOptions.Builder builder = build.getSocketOptions().getKeepAlive().mutate();
            // 保活配置
            builder.enable(true);
            builder.idle(Duration.ofSeconds(30));
            SocketOptions.Builder socketOptionsBuilder = clientOptions.getSocketOptions().mutate();
            SocketOptions.KeepAliveOptions keepAliveOptions = builder.build();
            socketOptionsBuilder.keepAlive(keepAliveOptions);
            SocketOptions socketOptions = socketOptionsBuilder.build();
            ClientOptions clientOptions1 = ClientOptions.builder().socketOptions(socketOptions).build();
            clientConfigurationBuilder.clientOptions(clientOptions1);
        };
    }
}
