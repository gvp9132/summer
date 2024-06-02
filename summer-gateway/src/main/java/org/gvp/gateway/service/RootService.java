package org.gvp.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class RootService {
    private final PasswordEncoder passwordEncoder;
    /**
     * 检查测试服务是否正常
     */
    public Mono<Result<Boolean>> check() {
        return Mono.just(Result.ok(true));
    }

    public String encryption(String password) {
        log.debug("加密密码: {}", password);
        if (StringUtils.hasLength(password)) {
            return this.passwordEncoder.encode(password);
        }
        return null;
    }
}
