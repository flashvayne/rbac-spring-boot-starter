package com.github.flashvayne.rbac.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.flashvayne.rbac.dto.AuthResourceDTO;
import com.github.flashvayne.rbac.dto.AuthRoleDTO;
import com.github.flashvayne.rbac.dto.AuthUserDTO;
import com.github.flashvayne.rbac.dto.RbacTokenInfo;
import com.github.flashvayne.rbac.property.RbacProperties;
import com.github.flashvayne.rbac.service.RbacTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;

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

    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    protected RedisTemplate redisTemplate;

    protected final RbacProperties rbacProperties;

    public DefaultRbacTokenServiceImpl(RbacProperties rbacProperties){
        this.rbacProperties = rbacProperties;
    }

    @Override
    public RbacTokenInfo generateTokenInfo(AuthUserDTO authUserDTO) {
        if(authUserDTO == null){
            return null;
        }
        Set<String> resources = new HashSet<>();
        List<AuthRoleDTO> authRoleDTOs = authUserDTO.getAuthRoleDTOList();
        if(!CollectionUtils.isEmpty(authRoleDTOs)){
            for (AuthRoleDTO authRoleDTO : authRoleDTOs){
                List<AuthResourceDTO> authResourceDTOs = authRoleDTO.getAuthResourceDTOList();
                if(!CollectionUtils.isEmpty(authResourceDTOs)){
                    for(int i= 0;i<authResourceDTOs.size();i++){
                        AuthResourceDTO authResourceDTO = authResourceDTOs.get(i);
                        if(authResourceDTO != null && authResourceDTO.getType() == (byte)1){
                            resources.add(authResourceDTO.getUrl());
                            authResourceDTOs.remove(i);
                            i--;
                        }
                    }
                }
            }
        }
        return new RbacTokenInfo(generateTokenString(authUserDTO),authUserDTO,null,resources);
    }

    @Override
    public boolean doGenerateToken(RbacTokenInfo rbacTokenInfo) {
        try {
            redisTemplate.opsForValue().set(rbacProperties.getRedisKeyPrefix()+rbacTokenInfo.getToken(),
                    om.writerWithDefaultPrettyPrinter().writeValueAsString(rbacTokenInfo)
                    , Duration.ofSeconds(rbacProperties.getTokenExpireTime()));
            log.info("doGenerateToken : {}", rbacTokenInfo);
            return true;
        } catch (Exception e) {
            log.error("doGenerateToken: ",e);
            return false;
        }
    }


    @Override
    public boolean refreshToken(String token) {
        try {
            if(Boolean.TRUE.equals(redisTemplate.expire(rbacProperties.getRedisKeyPrefix() + token,
                    Duration.ofSeconds(rbacProperties.getTokenExpireTime())))){
                log.info("refreshToken: {}", token);
                return true;
            }
        } catch (Exception e) {
            log.error("refreshToken: ",e);
        }
        return false;
    }

    @Override
    public boolean removeToken(String token) {
        try {
            if(Boolean.TRUE.equals(redisTemplate.delete(rbacProperties.getRedisKeyPrefix() + token))){
                log.info("removeToken: {}", token);
                return true;
            }
        } catch (Exception e) {
            log.error("removeToken: ",e);
        }
        return false;
    }

    @Override
    public RbacTokenInfo decodeTokenInfo(String token) {
        Object obj = redisTemplate.opsForValue().get(rbacProperties.getRedisKeyPrefix()+token);
        if(obj == null){
            log.error("decodeTokenInfo failed for token: {}, because it's not in redis",token);
            return null;
        }
        String tokenInfoJson = (String) obj;
        try {
            RbacTokenInfo tokenInfo = om.readValue(tokenInfoJson, RbacTokenInfo.class);
            log.info("decodeTokenInfo success for token: {}",token);
            return tokenInfo;
        } catch (Exception e) {
            log.error("decodeTokenInfo failed for token :{}, from json: {}, exception",token,tokenInfoJson,e);
            return null;
        }

    }

    @Override
    public RbacTokenInfo decodeAndRefreshToken(String token) {
        RbacTokenInfo tokenInfo = this.decodeTokenInfo(token);
        if(tokenInfo == null){
            return null;
        }
        refreshToken(token);
        return tokenInfo;
    }

    @Override
    public String generateTokenString(AuthUserDTO authUserDTO) {
        return (UUID.randomUUID() +UUID.randomUUID().toString())
                .replace("-","");
    }

}
