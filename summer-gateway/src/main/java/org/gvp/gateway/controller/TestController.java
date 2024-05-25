package org.gvp.gateway.controller;

import org.gvp.common.http.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/connect")
    public Mono<Result<String>> test(){
        return Mono.just(Result.ok("connect success"));
    }
}
