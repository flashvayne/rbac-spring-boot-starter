package com.github.flashvayne.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Rbac鉴权AOP
 *
 * @author flashvayne
 */
@Slf4j
@Aspect
@Component
public class RbacAuthorizationAspect {

    @Pointcut(value = "@annotation(com.github.flashvayne.config.RbacAuthorization)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object interceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        //todo
        return proceedingJoinPoint.proceed();
    }

    @After("pointcut()")
    public void doRemove(){
        //todo
    }

}
