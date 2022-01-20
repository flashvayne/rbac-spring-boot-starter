package com.github.flashvayne.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Token保存的信息
 *
 * @author flashvayne
 */
@Data
@AllArgsConstructor
public class TokenUserInfo {

    private String token;
    private AuthUserDTO authUserDTO;

    //Set集合冗余用户拥有的资源 方便高效鉴权
    private Set<String> urls;
}
