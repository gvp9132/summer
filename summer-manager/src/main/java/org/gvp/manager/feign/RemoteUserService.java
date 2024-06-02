package org.gvp.manager.feign;

import org.gvp.manager.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "summer-gateway",configuration = FeignConfig.class)
public interface RemoteUserService {

    @GetMapping("/root/pwd/encryption/{password}")
    String passwordEncryption(@PathVariable String password);
}
