package com.github.flashvayne.service.impl;

import com.github.flashvayne.dto.TokenUserInfo;
import com.github.flashvayne.mapper.UserMapper;
import com.github.flashvayne.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务默认实现
 *
 * @author flashvayne
 */
@Service
public class DefaultAuthUserService implements AuthUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean authentication(String userId, String password) {
        String originalPassword = userMapper.selectPassword(userId);
        return this.passwordVerification(originalPassword,password);
    }

    @Override
    public TokenUserInfo getTokenUserInfo(String userId) {


        return null;
    }

    @Override
    public boolean passwordVerification(String originalPassword, String providedPassword) {
        return providedPassword.equals(originalPassword);
    }


}
