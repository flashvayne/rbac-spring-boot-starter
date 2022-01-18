package com.github.flashvayne.utils;

import com.github.flashvayne.dto.TokenUserInfo;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Token工具类实现
 *
 * @author flashvayne
 */
@Component
public class TokenUtils extends AbstractTokenUtils {

    @Override
    String generateTokenString(TokenUserInfo tokenUserInfo) {
        return UUID.randomUUID().toString()+UUID.randomUUID().toString();
    }

}
