package com.github.flashvayne.rbac.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * 附加属性（用户可存储自定义的额外属性）
     */
    private Object addition;

    //Set集合冗余用户拥有的资源 通过contains方法高效鉴权
    @JsonIgnore
    private Set<String> resources;
}
