package org.gvp.gateway.service;

import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class RootService {
    /**
     * 检查测试服务是否正常
     */
    public Mono<Result<Boolean>> check() {
        return Mono.just(Result.ok(true));
    }
}
