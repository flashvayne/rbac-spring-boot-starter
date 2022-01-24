package com.github.flashvayne.rbac.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Rbac配置属性类
 *
 * @author flashvayne
 */
@Component
@ConfigurationProperties(prefix = "rbac")
public class RbacProperties {

    //token过期时间
    private Long tokenExpireTime = 7200L;

    //token属性名
    private String tokenName = "authorization";

    private String redisKeyPrefix = "rbac:";

    public Long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getRedisKeyPrefix() {
        return redisKeyPrefix;
    }

    public void setRedisKeyPrefix(String redisKeyPrefix) {
        this.redisKeyPrefix = redisKeyPrefix;
    }
}
