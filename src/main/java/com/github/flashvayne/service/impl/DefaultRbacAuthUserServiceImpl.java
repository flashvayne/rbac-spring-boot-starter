package com.github.flashvayne.service.impl;

import com.github.flashvayne.dto.AuthResourceDTO;
import com.github.flashvayne.dto.AuthRoleDTO;
import com.github.flashvayne.dto.AuthUserDTO;
import com.github.flashvayne.dto.RbacTokenInfo;
import com.github.flashvayne.mapper.BaseRbacMapper;
import com.github.flashvayne.service.RbacAuthUserService;
import com.github.flashvayne.service.RbacTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户信息服务默认实现
 * 可通过继承此类或实现AuthUserService接口，来重写用户信息相关功能
 *
 * @author flashvayne
 */
@Service
public class DefaultRbacAuthUserServiceImpl implements RbacAuthUserService {

    @Autowired
    private BaseRbacMapper userMapper;

    @Autowired
    private RbacTokenService tokenService;

    @Override
    public boolean authentication(String userId, String password) {
        String originalPassword = userMapper.selectPassword(userId);
        return this.passwordVerification(originalPassword,password);
    }

    @Override
    public RbacTokenInfo generateTokenInfo(String userId) {
        AuthUserDTO authUserDTO = userMapper.selectUser(userId);
        List<AuthRoleDTO> authRoleDTOs = userMapper.selectRole(userId);
        authUserDTO.setAuthRoleDTOList(authRoleDTOs);
        Set<String> resources = new HashSet<>();
        for (AuthRoleDTO authRoleDTO : authRoleDTOs){
            List<AuthResourceDTO> authResourceDTOs = userMapper.selectResource(authRoleDTO.getId());
            authRoleDTO.setAuthResourceDTOList(authResourceDTOs);
            for (AuthResourceDTO authResourceDTO : authResourceDTOs){
                resources.add(authResourceDTO.getUrl());
            }
        }
        return tokenService.generateTokenInfo(authUserDTO,resources);
    }

    @Override
    public boolean passwordVerification(String originalPassword, String providedPassword) {
        return providedPassword.equals(originalPassword);
    }


}
