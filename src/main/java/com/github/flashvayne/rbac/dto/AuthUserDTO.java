package com.github.flashvayne.rbac.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户
 *
 * @author flashvayne
 */
@Data
public class AuthUserDTO {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 状态 0.正常 1.禁用
     */
    private Byte status;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    private List<AuthRoleDTO> authRoleDTOList;
}
