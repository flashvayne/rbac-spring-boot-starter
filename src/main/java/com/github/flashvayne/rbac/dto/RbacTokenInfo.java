package com.github.flashvayne.rbac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * RbacToken信息
 *
 * @author flashvayne
 */
@Data
@AllArgsConstructor
public class RbacTokenInfo implements Serializable {

    private String token;
    private AuthUserDTO authUserDTO;

    //Set集合冗余用户拥有的资源 通过contains方法高效鉴权
    private Set<String> resources;
}
