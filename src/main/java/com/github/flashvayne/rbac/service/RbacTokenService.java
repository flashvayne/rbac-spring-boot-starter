package com.github.flashvayne.rbac.service;

import com.github.flashvayne.rbac.dto.AuthUserDTO;
import com.github.flashvayne.rbac.dto.RbacTokenInfo;

import java.util.Set;

/**
 * Token服务接口
 *
 * @author flashvayne
 */
public interface RbacTokenService {

     RbacTokenInfo generateTokenInfo(AuthUserDTO authUserDTO);

     boolean doGenerateToken(RbacTokenInfo rbacTokenInfo);

     String generateTokenString(AuthUserDTO authUserDTO);

     boolean refreshToken(String token);

     boolean removeToken(String token);

     RbacTokenInfo decodeTokenInfo(String token);

     RbacTokenInfo decodeAndRefreshToken(String token);

}
