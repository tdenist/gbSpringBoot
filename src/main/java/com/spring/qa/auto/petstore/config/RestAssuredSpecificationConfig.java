package com.spring.qa.auto.petstore.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestAssuredSpecificationConfig {

    @Value("${petstore.base.url}")
    private String BASE_URL;

    @Bean
    RequestSpecification requestPetStoreSpecification() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .log().all()
                .when()
                .contentType(ContentType.JSON);
    }
}
