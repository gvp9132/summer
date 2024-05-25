package org.gvp.gateway.global;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.common.http.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1)
@Log4j2
@Configuration
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private final ResponseHandler responseHandler;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("全局异常处理: ", ex);
        Result<String> result = Result.<String>empty(ex.getMessage());
        if (ex instanceof NotFoundException){
            log.error("服务没有找到: {}",ex.getMessage());
            result.setResponseCode(ResultCode.SERVICE_UNAVAILABLE);
            return responseHandler.responseWrite(exchange,result,HttpStatus.SERVICE_UNAVAILABLE);
        }
        result.setResponseCode(ResultCode.INTERNAL_SERVER_ERROR);
        result.setData(ex.getMessage());
        return responseHandler.responseWrite(exchange,result,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
