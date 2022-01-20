package com.github.flashvayne.service;

import com.github.flashvayne.dto.TokenUserInfo;

/**
 * 用户信息服务
 *
 * @author flashvayne
 */
public interface AuthUserService {

    boolean authentication(String userId,String password);

    TokenUserInfo getTokenUserInfo(String userId);

    boolean passwordVerification(String originalPassword,String providedPassword);
}
