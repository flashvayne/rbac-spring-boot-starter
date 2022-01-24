package com.github.flashvayne.rbac.utils;

import com.github.flashvayne.rbac.dto.RbacTokenInfo;

/**
 * 用户信息工具类
 * 经过认证和鉴权后，可在任意地方调用AuthUserUtils.get()方法获取从token解析到的用户信息
 *
 * @author flashvayne
 */
public class AuthUserUtils {

    private static final ThreadLocal<RbacTokenInfo> USER_INFO = new ThreadLocal<>();

    public static void set(RbacTokenInfo user) {
        USER_INFO.set(user);
    }

    public static RbacTokenInfo get() {
        return USER_INFO.get();
    }

    public static void remove() {
        USER_INFO.remove();
    }

}
