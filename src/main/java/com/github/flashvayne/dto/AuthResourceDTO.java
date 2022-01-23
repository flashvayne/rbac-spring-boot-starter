package com.github.flashvayne.dto;

import lombok.Data;

/**
 * 资源
 *
 * @author flashvayne
 */
@Data
public class AuthResourceDTO {
    /**
     * 资源id
     */
    private Integer id;

    /**
     * 类型 1.接口 2.菜单
     */
    private Byte type;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源标识/地址
     */
    private String url;
}
