package com.spring.qa.auto.petstore.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class PetStoreUserDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;
}
