package com.spring.qa.auto.mapper;

import com.spring.qa.auto.controller.dto.User;
import com.spring.qa.auto.entity.UserEntity;

public interface UserMapper {

    UserEntity dtoToEntity(User userDto);

    User entityToDto(UserEntity userEntity);
}
