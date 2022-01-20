package com.github.flashvayne.service.impl;

import com.github.flashvayne.dto.AuthResourceDTO;
import com.github.flashvayne.dto.AuthRoleDTO;
import com.github.flashvayne.dto.AuthUserDTO;
import com.github.flashvayne.dto.TokenUserInfo;
import com.github.flashvayne.mapper.UserMapper;
import com.github.flashvayne.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户信息服务默认实现
 *
 * @author flashvayne
 */
@Service
public class DefaultAuthUserServiceImpl implements AuthUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DefaultTokenServiceImpl tokenService;

    @Override
    public boolean authentication(String userId, String password) {
        String originalPassword = userMapper.selectPassword(userId);
        return this.passwordVerification(originalPassword,password);
    }

    @Override
    public TokenUserInfo generateTokenUserInfo(String userId) {
        AuthUserDTO authUserDTO = userMapper.selectUser(userId);
        List<AuthRoleDTO> authRoleDTOs = userMapper.selectRole(userId);
        authUserDTO.setAuthRoleDTOList(authRoleDTOs);
        Set<String> urls = new HashSet<>();
        for (AuthRoleDTO authRoleDTO : authRoleDTOs){
            List<AuthResourceDTO> authResourceDTOs = userMapper.selectResource(authRoleDTO.getId());
            authRoleDTO.setAuthResourceDTOList(authResourceDTOs);
            for (AuthResourceDTO authResourceDTO : authResourceDTOs){
                urls.add(authResourceDTO.getUrl());
            }
        }
        TokenUserInfo tokenUserInfo = tokenService.generateTokenUserInfo(authUserDTO,urls);
        return tokenUserInfo;
    }

    @Override
    public boolean passwordVerification(String originalPassword, String providedPassword) {
        return providedPassword.equals(originalPassword);
    }


}
