package org.gvp.gateway.service;

import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.pojo.SecurityUser;
import org.gvp.gateway.repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 用户服务
 */
@Log4j2
@Service
public class SecurityUserService implements ReactiveUserDetailsService {
    private final SecurityUserRepository userRepository;
    @Autowired
    public SecurityUserService(SecurityUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 使用用户名查找用户信息
     * @param username 用户名
     */
    public Mono<SecurityUser> findUserByUsername(String username){
        log.debug("使用用户名查找用户信息: {}",username);
        return this.userRepository.searchByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("用户[ %s ]不存在".formatted(username))));
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.findUserByUsername(username).cast(UserDetails.class);
    }
}
