package com.github.flashvayne.service;

import com.github.flashvayne.dto.AuthUserDTO;
import com.github.flashvayne.dto.RbacTokenInfo;

import java.util.Set;

/**
 * Token服务接口
 *
 * @author flashvayne
 */
public interface RbacTokenService {

    public RbacTokenInfo generateTokenInfo(AuthUserDTO authUserDTO, Set<String> resources);

    public String generateTokenString(AuthUserDTO authUserDTO);

    public boolean refreshToken(String token);

    public boolean removeToken(String token);

    public RbacTokenInfo decodeTokenInfo(String token);

    public RbacTokenInfo decodeAndRefreshToken(String token);

}
