package com.github.flashvayne.rbac.mapper;

import com.github.flashvayne.rbac.dto.AuthResourceDTO;
import com.github.flashvayne.rbac.dto.AuthRoleDTO;
import com.github.flashvayne.rbac.dto.AuthUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Rbac Dao
 * Dao基础接口，本项目提供了MySQL的实现RbacMapper-MySQL.xml
 * 其他数据源请继承此接口进行实现
 *
 * @author flashvayne
 */
@Mapper
public interface BaseRbacMapper {
    String selectPassword(String userId);
    AuthUserDTO selectUser(String userId);
    List<AuthRoleDTO> selectRole(String userId);
    List<AuthResourceDTO> selectResource(Integer roleId);
}
