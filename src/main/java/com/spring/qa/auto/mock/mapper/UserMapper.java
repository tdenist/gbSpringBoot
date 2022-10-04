package com.spring.qa.auto.mock.mapper;

import com.spring.qa.auto.mock.controller.dto.User;
import com.spring.qa.auto.mock.entity.UserEntity;
public interface UserMapper {

    UserEntity dtoToEntity(User userDto);

    User entityToDto(UserEntity userEntity);
}
