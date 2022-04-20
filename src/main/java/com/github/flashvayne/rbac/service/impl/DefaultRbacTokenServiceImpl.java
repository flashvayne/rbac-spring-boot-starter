package com.github.flashvayne.rbac.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.flashvayne.rbac.dto.AuthUserDTO;
import com.github.flashvayne.rbac.dto.RbacTokenInfo;
import com.github.flashvayne.rbac.property.RbacProperties;
import com.github.flashvayne.rbac.service.RbacTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

/**
 * Token服务默认实现
 * 可通过继承此类或实现TokenService接口，来重写Token服务相关功能
 *
 * @author flashvayne
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class DefaultRbacTokenServiceImpl implements RbacTokenService {

    @Autowired
    protected RedisTemplate redisTemplate;

    protected final RbacProperties rbacProperties;

    public DefaultRbacTokenServiceImpl(RbacProperties rbacProperties){
        this.rbacProperties = rbacProperties;
    }

    @Override
    public RbacTokenInfo generateTokenInfo(AuthUserDTO authUserDTO, Set<String> resources) {
        String token = generateTokenString(authUserDTO);
        RbacTokenInfo tokenInfo = new RbacTokenInfo(token,authUserDTO,null,resources);
        try {
            redisTemplate.opsForValue().set(rbacProperties.getRedisKeyPrefix()+token, JSONObject.toJSONString(tokenInfo), Duration.ofSeconds(rbacProperties.getTokenExpireTime()));
            log.info("generateToken: {},userInfo: {}", token,tokenInfo);
            return tokenInfo;
        } catch (Exception e) {
            log.error("生成token异常",e);
            return null;
        }
    }

    @Override
    public boolean refreshToken(String token) {
        try {
            if(redisTemplate.expire(rbacProperties.getRedisKeyPrefix()+token, Duration.ofSeconds(rbacProperties.getTokenExpireTime()))){
                log.info("refreshToken: {}", token);
                return true;
            }
        } catch (Exception e) {
            log.error("刷新token异常",e);
        }
        return false;
    }

    @Override
    public boolean removeToken(String token) {
        try {
            if(redisTemplate.delete(rbacProperties.getRedisKeyPrefix()+token)){
                log.info("removeToken: {}", token);
                return true;
            }
        } catch (Exception e) {
            log.error("刷新token异常",e);
        }
        return false;
    }

    @Override
    public RbacTokenInfo decodeTokenInfo(String token) {
        String tokenInfoJson = (String) redisTemplate.opsForValue().get(rbacProperties.getRedisKeyPrefix()+token);
        RbacTokenInfo tokenInfo = JSONObject.parseObject(tokenInfoJson, RbacTokenInfo.class);
        log.info("decodeTokenInfo: {}",token);
        return tokenInfo;
    }

    @Override
    public RbacTokenInfo decodeAndRefreshToken(String token) {
        RbacTokenInfo tokenInfo = this.decodeTokenInfo(token);
        refreshToken(token);
        return tokenInfo;
    }

    @Override
    public String generateTokenString(AuthUserDTO authUserDTO) {
        return (UUID.randomUUID().toString()+UUID.randomUUID().toString())
                .replace("-","");
    }

}
