package com.github.flashvayne.service;

import com.github.flashvayne.dto.RbacTokenInfo;

/**
 * 用户信息服务接口
 *
 * @author flashvayne
 */
public interface RbacAuthUserService {

    boolean authentication(String userId,String password);

    RbacTokenInfo generateTokenInfo(String userId);

    boolean passwordVerification(String originalPassword,String providedPassword);
}
