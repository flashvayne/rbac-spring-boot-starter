package com.github.flashvayne.autoconfig;

import com.github.flashvayne.aspect.RbacAuthorizationAspect;
import com.github.flashvayne.property.RbacProperties;
import com.github.flashvayne.service.AuthUserService;
import com.github.flashvayne.service.TokenService;
import com.github.flashvayne.service.impl.DefaultAuthUserServiceImpl;
import com.github.flashvayne.service.impl.DefaultTokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rbac自动装配
 * 装配AOP、用户服务、Token服务
 * 需要配置属性rbac.enable=true 来启用Rbac-spring-boot-start
 *
 * 使用@ConditionalOnMissingBean进行装配，所以以上服务均可被重写并注入。例如：
 * 需要密码加密，只需继承DefaultAuthUserServiceImpl，重写passwordVerification方法并注入容器即可
 *
 * @author flashvayne
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RbacProperties.class)
@ConditionalOnProperty(value = "rbac.enable",havingValue = "true")
public class RbacAutoConfiguration {

    public RbacAutoConfiguration(){
        log.info("rbac-springboot-start loaded.");
    }

    @Autowired
    private RbacProperties rbacProperties;

    @Bean
    @ConditionalOnMissingBean(RbacAuthorizationAspect.class)
    public RbacAuthorizationAspect RbacAuthorizationAspect(){
        RbacAuthorizationAspect rbacAuthorizationAspect = new RbacAuthorizationAspect();
        return rbacAuthorizationAspect;
    }

    @Bean
    @ConditionalOnMissingBean(AuthUserService.class)
    public AuthUserService AuthUserService(){
        AuthUserService authUserService = new DefaultAuthUserServiceImpl();
        return authUserService;
    }

    @Bean
    @ConditionalOnMissingBean(DefaultTokenServiceImpl.class)
    public TokenService TokenService(){
        TokenService tokenService = new DefaultTokenServiceImpl(rbacProperties);
        return tokenService;
    }

}
