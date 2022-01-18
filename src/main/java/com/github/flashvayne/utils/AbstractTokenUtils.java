package com.github.flashvayne.utils;

import com.github.flashvayne.dto.TokenUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * Token抽象工具类
 *
 * @author flashvayne
 */
@Slf4j
public abstract class AbstractTokenUtils {

    private Integer TOKEN_EXPIRE_TIME = 7200;

    @Autowired
    private RedisTemplate redisTemplate;

    public String generateToken(TokenUserInfo tokenUserInfo) {
        String token = generateTokenString(tokenUserInfo);
        try {
            redisTemplate.opsForValue().set(token,tokenUserInfo, Duration.ofSeconds(TOKEN_EXPIRE_TIME));
            log.info("generateToken: {}", token);
            return token;
        } catch (Exception e) {
            log.error("生成token异常",e);
            return null;
        }
    }

    abstract String generateTokenString(TokenUserInfo tokenUserInfo);

    public boolean refreshToken(String token) {
        try {
            if(redisTemplate.expire(token, Duration.ofSeconds(TOKEN_EXPIRE_TIME))){
                log.info("refreshToken: {}", token);
                return true;
            }
        } catch (Exception e) {
            log.error("刷新token异常",e);
        }
        return false;
    }

    public boolean removeToken(String token) {
        try {
            if(redisTemplate.delete(token)){
                log.info("removeToken: {}", token);
                return true;
            }
        } catch (Exception e) {
            log.error("刷新token异常",e);
        }
        return false;
    }

    public TokenUserInfo decodeTokenUserInfo(String token) {
        TokenUserInfo tokenUserInfo = (TokenUserInfo) redisTemplate.opsForValue().get(token);
        log.info("decodeTokenUserInfo: {}, userInfo: {}",token,tokenUserInfo);
        return tokenUserInfo;
    }

}
