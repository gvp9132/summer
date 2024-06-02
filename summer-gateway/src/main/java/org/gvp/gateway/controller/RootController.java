package org.gvp.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.gvp.common.http.Result;
import org.gvp.gateway.dto.MysqlReplica;
import org.gvp.gateway.service.MySqlInfoService;
import org.gvp.gateway.service.RootService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/root")
@RequiredArgsConstructor
public class RootController {
    private final RootService rootService;
    private final MySqlInfoService mySqlInfoService;

    /**
     * 检查测试服务是否正常
     */
    @GetMapping("/status/connect")
    public Mono<Result<Boolean>> check(){
        return this.rootService.check();
    }

    @GetMapping("/status/mysql")
    public Mono<Result<MysqlReplica>> queryMysqlInfo(){
        return this.mySqlInfoService.getMysqlInfo();
    }


    @GetMapping("/pwd/encryption/{password}")
    public Mono<Result<String>> encryption(@PathVariable String password){
       return Mono.just(Result.<String>ok(this.rootService.encryption(password)));
    }
}
