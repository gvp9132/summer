package org.gvp.gateway.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 用户请求响应处理器
 * @version 2.0
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class ResponseHandler {
    private final ObjectMapper objectMapper;

    /**
     * 响应数据写出到响应体
     * @param exchange 服务器web交换对象
     * @param data 需要返回给客户端的数据
     */
    public Mono<Void> responseWrite(ServerWebExchange exchange, Object data) {
        // 设置响应的内容和状态码
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return Mono.defer(
                () -> Mono.just(exchange.getResponse())
                        .flatMap(response -> response.writeWith(Mono.just(response.bufferFactory().wrap(toJsonByte(data)))))
        );
    }

    /**
     * 将需要反回给客户端的数据序列化为json
     */
    private byte[] toJsonByte(Object data) {
        try {
            return this.objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("序列化json失败: {},{}", data, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
