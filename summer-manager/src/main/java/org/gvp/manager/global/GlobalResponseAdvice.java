package org.gvp.manager.global;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局返回值处理,只处理自定义控制层
 */
//@Log4j2
//@RestControllerAdvice(basePackages = "org.gvp.summer.manager.controller")
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
       //TODO: 可以在这里处理返回类型是否是包装类型
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 如果反回类型是包装的反回类型就不处理
//        if (body instanceof JsonResult<?>){
//            return body ;
//        }
//        return JsonResultBuild.ok(body) ;
        return null;
    }
}
