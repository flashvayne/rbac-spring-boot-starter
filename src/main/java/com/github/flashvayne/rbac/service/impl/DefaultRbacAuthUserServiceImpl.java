package com.github.flashvayne.rbac.service.impl;

import com.github.flashvayne.rbac.dto.AuthResourceDTO;
import com.github.flashvayne.rbac.dto.AuthRoleDTO;
import com.github.flashvayne.rbac.dto.AuthUserDTO;
import com.github.flashvayne.rbac.mapper.BaseRbacMapper;
import com.github.flashvayne.rbac.service.RbacAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息服务默认实现
 * 可通过继承此类或实现AuthUserService接口，来重写用户信息相关功能
 *
 * @author flashvayne
 */
@Service
public class DefaultRbacAuthUserServiceImpl implements RbacAuthUserService {

    @Autowired
    protected BaseRbacMapper userMapper;

    @Override
    public boolean authentication(String userId, String password) {
        String originalPassword = userMapper.selectPassword(userId);
        return this.passwordVerification(originalPassword,password);
    }

    @Override
    public AuthUserDTO getAuthUserInfo(String userId) {
        AuthUserDTO authUserDTO = userMapper.selectUser(userId);
        List<AuthRoleDTO> authRoleDTOs = userMapper.selectRole(userId);
        authUserDTO.setAuthRoleDTOList(authRoleDTOs);
        for (AuthRoleDTO authRoleDTO : authRoleDTOs){
            List<AuthResourceDTO> authResourceDTOs = userMapper.selectResource(authRoleDTO.getId());
            authRoleDTO.setAuthResourceDTOList(authResourceDTOs);
        }
        return authUserDTO;
    }

    @Override
    public boolean passwordVerification(String originalPassword, String providedPassword) {
        return providedPassword.equals(originalPassword);
    }


}
