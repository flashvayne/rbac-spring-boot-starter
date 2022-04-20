package com.github.flashvayne.rbac.aspect;

import com.github.flashvayne.rbac.dto.RbacTokenInfo;
import com.github.flashvayne.rbac.property.RbacProperties;
import com.github.flashvayne.rbac.service.RbacTokenService;
import com.github.flashvayne.rbac.utils.AuthUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Rbac鉴权AOP
 * 拦截有@RbacAuthorization注解的方法进行认证和鉴权
 * 可通过继承此类修改拦截器的实现
 *
 * @author flashvayne
 */
@Slf4j
@Aspect
@Component
@Order(100)
public class RbacAuthorizationAspect {

    @Autowired
    private RbacTokenService tokenService;

    @Autowired
    private RbacProperties rbacProperties;

    @Pointcut(value = "@annotation(com.github.flashvayne.rbac.aspect.RbacAuthorization)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object interceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(servletRequestAttributes).getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        String token = getToken(request);
        if(StringUtils.isBlank(token)){
            log.warn("未获取到token，身份认证失败");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
        RbacTokenInfo tokenInfo = tokenService.decodeAndRefreshToken(token);
        if(tokenInfo == null){
            log.warn("token校验失败，或已过期");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
        String uri = request.getRequestURI();
        if(CollectionUtils.isEmpty(tokenInfo.getResources()) || !tokenInfo.getResources().contains(uri)){
            log.warn("无权限完成操作");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return null;
        }
        AuthUserUtils.set(tokenInfo);
        return proceedingJoinPoint.proceed();
    }

    @After("pointcut()")
    public void doRemove(){
        AuthUserUtils.remove();
    }

    private String getToken(HttpServletRequest request){
        return request.getHeader(rbacProperties.getTokenName());
    }

}
