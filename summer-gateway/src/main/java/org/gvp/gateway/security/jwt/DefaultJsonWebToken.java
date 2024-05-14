package org.gvp.gateway.security.jwt;

import lombok.extern.log4j.Log4j2;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.gvp.common.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * 默认jwt操作接口实现类
 * @version V2.0
 * @author gvp9132
 */
@Log4j2
@Component
public class DefaultJsonWebToken implements JsonWebToken<TokenInfo>{
    private final JsonWebTokenProperties properties;
    private final Algorithm algorithm;
    @Autowired
    public DefaultJsonWebToken(JsonWebTokenProperties properties) {
        this.properties = properties;
        this.algorithm = Algorithm.HMAC256(properties.getSecret());
    }
    @Override
    public TokenInfo createToken(String payload) {
        log.debug("create token with payload: {}", payload);
        TokenInfo tokenInfo = new TokenInfo();
        String tokenId = StringUtil.uuid32();
        Instant created = Instant.now();
        Instant expire = created.plusSeconds(this.properties.getExpireTime());
        JWTCreator.Builder builder = JWT.create()
                .withJWTId(this.properties.getJwtId())
                .withIssuer(this.properties.getIssuer())
                .withAudience(this.properties.getAudience())
                .withIssuedAt(created)
                .withExpiresAt(expire)
                .withSubject(payload)
                .withClaim(JsonWebToken.TOKEN_ID_KEY, tokenId);
        try {
            tokenInfo.setToken(builder.sign(this.algorithm));
            tokenInfo.setTokenId(tokenId);
            // token创建时间和过期,增加8小时时差
            tokenInfo.setCreateTime(created.plus(this.properties.getClockSkew(), ChronoUnit.HOURS));
            tokenInfo.setExpireTime(expire.plus(this.properties.getClockSkew(), ChronoUnit.HOURS));
//            tokenInfo.setCreateTime(created);
//            tokenInfo.setExpireTime(expire);
            tokenInfo.setUsername(payload);
        }catch (JWTCreationException e){
            log.error("create user token failed", e);
            tokenInfo.setError(true);
            tokenInfo.setErrorMessage(e.getMessage());
            return null ;
        }catch (IllegalArgumentException ae){
            log.error("create user token parameter illegal", ae);
            tokenInfo.setError(true);
            tokenInfo.setErrorMessage(ae.getMessage());
            return null ;
        }
        log.debug("create token success: {}", tokenInfo);
        return tokenInfo;
    }
    @Override
    public TokenInfo parseToken(String token) {
        log.debug("parse token: {}", token);
        TokenInfo tokenInfo = new TokenInfo();
        if (!StringUtils.hasText(token) || !token.startsWith(JsonWebToken.TOKEN_PREFIX)){
            log.error("token is empty or not start with Bearer");
            tokenInfo.setError(true);
            tokenInfo.setErrorMessage("token is empty or not start with Bearer");
            return tokenInfo;
        }
        try {
            DecodedJWT verify = JWT.require(this.algorithm)
                    .withJWTId(this.properties.getJwtId())
                    .withIssuer(this.properties.getIssuer())
                    .build().verify(token.substring(JsonWebToken.TOKEN_PREFIX.length()));
            tokenInfo.setCreateTime(verify.getIssuedAtAsInstant());
            tokenInfo.setExpireTime(verify.getExpiresAtAsInstant());
            tokenInfo.setUsername(verify.getSubject());
            tokenInfo.setTokenId(verify.getClaim(JsonWebToken.TOKEN_ID_KEY).asString());
        }catch (IllegalArgumentException ae){
            log.error("parse token parameter illegal", ae);
            tokenInfo.setError(true);
            tokenInfo.setErrorMessage(ae.getMessage());
        }catch (TokenExpiredException ee){
            log.error("token is expired", ee);
            tokenInfo.setExpired(true);
            tokenInfo.setExpireTime(ee.getExpiredOn());
            tokenInfo.setErrorMessage(ee.getMessage());
        }catch (JWTVerificationException ve){
            log.error("token verify failed", ve);
            tokenInfo.setError(true);
            tokenInfo.setErrorMessage(ve.getMessage());
        }
        return tokenInfo;
    }
}
