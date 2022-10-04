package com.spring.qa.auto.mock.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address_entity")
@Getter
@Setter
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "city_name")
    private String city;

    @Column(name = "street_name")
    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    @OneToMany(mappedBy = "addressEntity")
    List<UserEntity> users = new ArrayList<>();
}
