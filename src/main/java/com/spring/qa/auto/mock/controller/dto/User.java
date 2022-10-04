package com.spring.qa.auto.mock.controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String firstName;
    private String secondName;
    private int age;
}
