package com.spring.qa.auto.service;


import com.spring.qa.auto.controller.dto.User;

public interface UserService {

    void save(User userDto);

    void update(User userDto);

    User getById(Integer id);

    void delete(Integer id);
}
