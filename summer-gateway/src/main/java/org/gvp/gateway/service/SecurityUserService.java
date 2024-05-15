package org.gvp.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.gateway.cache.SecurityUserCacheHandler;
import org.gvp.gateway.pojo.SecurityRole;
import org.gvp.gateway.pojo.SecurityUser;
import org.gvp.gateway.repository.SecurityRoleRepository;
import org.gvp.gateway.repository.SecurityUserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 用户服务
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityUserService implements ReactiveUserDetailsService {
    private final SecurityUserRepository userRepository;
    private final SecurityRoleRepository roleRepository;
    private final SecurityUserCacheHandler cacheHandler;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
       return cacheHandler
               .get(username)
               .cast(UserDetails.class)
               .switchIfEmpty(findUser(username));
    }
    /**
     * 从数据库中查找用户信息并缓存
     */
    private Mono<UserDetails> findUser(String username) {
        return this.userRepository
                .searchByUsername(username)
                .flatMap(this::loadUserRole)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("用户[ %s ]不存在".formatted(username))))
                .flatMap(user -> cacheHandler.save(user.getUsername(),user).thenReturn(user))
                .cast(UserDetails.class);
    }
    /**
     * 装载用户的角色信息
     * @param user 用户信息
     * @return 装载角色信息后的用户信息
     */
    public Mono<SecurityUser> loadUserRole(SecurityUser user){
        Mono<List<SecurityRole>> listMono = roleRepository.searchRolesByUserId(user.getId()).collectList();
        return listMono.map(roles -> {
            user.setAuthorities(roles);
            return user;
        });
    }
}
