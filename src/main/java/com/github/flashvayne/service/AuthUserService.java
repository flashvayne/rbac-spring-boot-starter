package com.github.flashvayne.service;

import com.github.flashvayne.dto.TokenUserInfo;

/**
 * 用户信息服务
 *
 * @author flashvayne
 */
public interface AuthUserService {

    TokenUserInfo getTokenUserInfo(String userId);

}
