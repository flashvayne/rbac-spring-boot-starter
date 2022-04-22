package com.github.flashvayne.rbac.service;

import com.github.flashvayne.rbac.dto.AuthUserDTO;

/**
 * 用户信息服务接口
 *
 * @author flashvayne
 */
public interface RbacAuthUserService {

    boolean authentication(String userId,String password);

    AuthUserDTO getAuthUserInfo(String userId);

    boolean passwordVerification(String originalPassword,String providedPassword);
}
