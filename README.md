# 


## 项目命名
* https://www.51test.net/show/5854275.html


## 项目相关

* http相关常量使用org.springframework.http.HttpHeaders中提供的
* 响应token可以放置在响应的响应头中

### mysql日期时间
* https://juejin.cn/post/7290382189992165415
* 性能: bigint > datetime > timestamp

## gateway

* 根据用户名查找用户和角色

```java
   /**
     * 根据用户名查找用户信息,用户信息结果包含用户角色信息
     */
    public Mono<SecurityUser> findByUsernameAndRoleList(String username) {
        Mono<SecurityUser> user = databaseClient
                .sql("""
                        select u.id, u.username,u.password ,u.identity,u.enabled,u.account_non_locked,u.account_non_expired,u.credentials_non_expired,r.id as roleId,r.authority
                        from security_user u
                            left join security_user_role ur
                                on u.id = ur.user_id and u.`delete`=false and ur.`delete`=false
                            left join security_role r
                                on ur.role_id = r.id and r.`delete`=false where u.username=?  
                        """)
                .bind(0, username)
                .fetch()
                .all()
                .bufferUntilChanged(rowMap -> rowMap.get("username"))
                .map(ele -> {

                    SecurityUser securityUser = new SecurityUser();
                    String userId = ele.get(0).get("id").toString();
                    String userName = ele.get(0).get("username").toString();
                    String password = ele.get(0).get("password").toString();
                    String identity = ele.get(0).get("identity").toString();
                    String enabled = ele.get(0).get("enabled").toString();
                    String accountNonLocked = ele.get(0).get("account_non_locked").toString();
                    String accountNonExpired = ele.get(0).get("account_non_expired").toString();
                    String credentialsNonExpired = ele.get(0).get("credentials_non_expired").toString();

                    securityUser.setId(Integer.parseInt(userId));
                    securityUser.setUsername(userName);
                    securityUser.setPassword(password);
                    securityUser.setIdentity(identity);
                    securityUser.setEnabled(Integer.parseInt(enabled) == 1);
                    securityUser.setAccountNonLocked(Integer.parseInt(accountNonLocked) == 1);
                    securityUser.setAccountNonExpired(Integer.parseInt(accountNonExpired) == 1);
                    securityUser.setCredentialsNonExpired(Integer.parseInt(credentialsNonExpired) == 1);
                    List<GrantedAuthority> authorities = new ArrayList<>() ;

                    ele.forEach(e -> {
                        SecurityRole securityRole = new SecurityRole(Integer.parseInt(e.get("roleId").toString()), e.get("authority").toString());
                        authorities.add(securityRole);
                    } );

                    securityUser.setAuthorities(authorities) ;

                    return securityUser;
                }).next() ;
        return user.defaultIfEmpty(new SecurityUser());
    }

```


## 提交格式

* 提交类型 
  * feat: 新功能
  * fix: 修复问题(bug)
  * docs: 修改文档
  * style: 修改代码格式，不影响代码逻辑
  * refactor: 重构代码，理论上不影响现有功能
  * perf: 提升性能
  * test: 增加修改测试用例
  * chore: 修改工具相关（包括但不限于文档、代码生成等）添加依赖 修改格式等
  * deps: 升级依赖
  * revert: 回滚版本
  * build: 构建系统或者包依赖更新
  * other: 其他不重要修改
* 范围Scope
    * 用于说明本次修改涉及的范围-模块
* 主题 Subject
* 详细说明 Body

* 例如:
```
<type>(<scope>): <subject>
<空行>
<body>
<空行>
<footer>
**************************************
feat(模块): 新增功能
-- 空行
详细说明
```