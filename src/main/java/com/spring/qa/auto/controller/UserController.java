package com.spring.qa.auto.controller;

import com.spring.qa.auto.controller.dto.User;
import com.spring.qa.auto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-rest")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    void save(@RequestBody User user) {
        userService.save(user);
    }

    @PutMapping
    void update(@RequestBody User user) {
        userService.update(user);
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable Integer id) {
        userService.delete(id);
    }
}
