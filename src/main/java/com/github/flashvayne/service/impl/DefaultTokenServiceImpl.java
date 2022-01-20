package com.github.flashvayne.service.impl;

import com.github.flashvayne.dto.AuthUserDTO;
import com.github.flashvayne.dto.TokenUserInfo;
import com.github.flashvayne.service.AbstractTokenService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Token工具类实现
 *
 * @author flashvayne
 */
@Component
public class DefaultTokenServiceImpl extends AbstractTokenService {

    @Override
    public String generateTokenString(AuthUserDTO authUserDTO) {
        return UUID.randomUUID().toString()+UUID.randomUUID().toString();
    }

}
