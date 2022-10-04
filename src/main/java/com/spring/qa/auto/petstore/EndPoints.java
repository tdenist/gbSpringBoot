package com.spring.qa.auto.petstore;

public enum EndPoints {

    USER("/user"),
    STORE("/store"),
    PET("/pet");

    private final String url;

    EndPoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
