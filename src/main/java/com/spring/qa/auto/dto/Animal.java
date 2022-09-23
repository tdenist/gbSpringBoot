package com.spring.qa.auto.dto;

public class Animal {

    private String kind;
    private Boolean isWild;
    private String name;
    private int age;

    public Animal() {
    }

    public Animal(String kind, Boolean isWild, String name, int age) {
        this.kind = kind;
        this.isWild = isWild;
        this.name = name;
        this.age = age;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Boolean getWild() {
        return isWild;
    }

    public void setWild(Boolean wild) {
        isWild = wild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
