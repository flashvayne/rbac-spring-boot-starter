package com.github.flashvayne.rbac.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色
 *
 * @author flashvayne
 */
@Data
public class AuthRoleDTO {
    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    private List<AuthResourceDTO> authResourceDTOList;
}
