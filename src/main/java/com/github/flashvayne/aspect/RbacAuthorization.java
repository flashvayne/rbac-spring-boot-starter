package com.github.flashvayne.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Rbac鉴权注解
 * 在需要身份验证及权限校验的方法上使用此注解
 *
 * @author flashvayne
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RbacAuthorization {

}