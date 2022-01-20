package com.github.flashvayne.mapper;

import com.github.flashvayne.dto.AuthResourceDTO;
import com.github.flashvayne.dto.AuthRoleDTO;
import com.github.flashvayne.dto.AuthUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    String selectPassword(String userId);
    AuthUserDTO selectUser(String userId);
    List<AuthRoleDTO> selectRole(String userId);
    List<AuthResourceDTO> selectResource(Integer roleId);
}
