package com.github.flashvayne.service;

import com.github.flashvayne.dto.AuthUserDTO;
import com.github.flashvayne.dto.TokenUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Set;

/**
 * Token抽象工具类
 *
 * @author flashvayne
 */
@Slf4j
public abstract class AbstractTokenService {

    private Integer TOKEN_EXPIRE_TIME = 7200;

    @Autowired
    private RedisTemplate redisTemplate;

    public TokenUserInfo generateTokenUserInfo(AuthUserDTO authUserDTO, Set<String> urls) {
        String token = generateTokenString(authUserDTO);
        TokenUserInfo tokenUserInfo = new TokenUserInfo(token,authUserDTO,urls);
        try {
            redisTemplate.opsForValue().set(token,authUserDTO, Duration.ofSeconds(TOKEN_EXPIRE_TIME));
            log.info("generateToken: {}", token);
            return tokenUserInfo;
        } catch (Exception e) {
            log.error("生成token异常",e);
            return null;
        }
    }

    public abstract String generateTokenString(AuthUserDTO authUserDTO);

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
