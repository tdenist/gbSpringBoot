package com.spring.qa.auto.mock.service;

import com.spring.qa.auto.mock.controller.dto.User;

public interface UserService {

    void save(User userDto);

    void update(User userDto);

    User getById(Integer id);

    void delete(Integer id);
}
