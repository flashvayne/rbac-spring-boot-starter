package com.github.flashvayne.mapper;

import com.github.flashvayne.dto.AuthUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    String selectPassword(String userId);
    AuthUserDTO selectUser(String userId);

}
