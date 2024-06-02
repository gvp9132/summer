package org.gvp.manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.cache.CacheHandler;
import org.gvp.common.http.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Log4j2
@RequestMapping("/manager/admin/setting")
@RestController
@RequiredArgsConstructor
public class SettingConfig {
    private final CacheHandler<Object> cacheHandler;
    private final String SETTING_KEY = "gvp:admin:setting:";
    @PutMapping()
    public Result<Boolean> saveConfig(@RequestBody HashMap<String,Object> config){
        log.debug("保存用户配置: {}",config);
        if (config.get("username") !=null &&  StringUtils.hasLength(config.get("username").toString())){
            this.cacheHandler.save(SETTING_KEY + config.get("username"), config);
            return Result.ok(true);
        }
        return Result.ok(false);
    }

    @GetMapping("/{username}")
    public Result<Object> queryConfig(@PathVariable String username){
        log.debug("读取用户配置");
        return Result.ok(this.cacheHandler.get(SETTING_KEY + username));
    }
}
