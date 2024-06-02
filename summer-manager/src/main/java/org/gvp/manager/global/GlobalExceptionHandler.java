package org.gvp.manager.global;

import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常: {}", e.getClass());
        log.error("系统异常: ", e);
        return Result.fail(e.getMessage());
    }
}
