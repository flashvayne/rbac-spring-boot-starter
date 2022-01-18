package com.github.flashvayne.utils;

import com.github.flashvayne.dto.TokenUserInfo;

/**
 * 用户信息工具类
 *
 * @author flashvayne
 */
public class AuthUserUtils {

    private static final ThreadLocal<TokenUserInfo> USER_INFO = new ThreadLocal<>();

    public static void set(TokenUserInfo user) {
        USER_INFO.set(user);
    }

    public static TokenUserInfo get() {
        return USER_INFO.get();
    }

    public static void remove() {
        USER_INFO.remove();
    }

}
