package com.spring.qa.auto.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.qa.auto.controller.dto.User;
import com.spring.qa.auto.entity.UserEntity;
import com.spring.qa.auto.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void saveUserTest() throws Exception {
        //pre-condition
        User user = new User();
        user.setAge(50);
        user.setFirstName("First Name");
        user.setSecondName("Second Name");

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(user)))
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //step 2
        UserEntity userEntity = userRepository.findAll().stream()
                .findFirst()
                        .orElseThrow();

        //assert
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userEntity.getFirstName()).isEqualTo(user.getFirstName());
            s.assertThat(userEntity.getSecondName()).isEqualTo(user.getSecondName());
            s.assertThat(userEntity.getAge()).isEqualTo(user.getAge());
        });
    }

    @Test
    void getUserTest() throws Exception {
        //pre-condition
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setSecondName("Second Name");
        userEntity.setAge(50);
        UserEntity entity = userRepository.saveAndFlush(userEntity);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + entity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        User user = objectMapper.readValue(response.body(), User.class);
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userEntity.getFirstName()).isEqualTo(user.getFirstName());
            s.assertThat(userEntity.getSecondName()).isEqualTo(user.getSecondName());
            s.assertThat(userEntity.getAge()).isEqualTo(user.getAge());
        });
    }

    @Test
    //@Disabled
    void updateUserTest() throws Exception {
        //pre-condition
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setSecondName("Second Name");
        userEntity.setAge(50);
        userRepository.saveAndFlush(userEntity);

        User userUpdate = new User();
        userUpdate.setFirstName("First Name Update");
        userUpdate.setSecondName("Second Name Update");
        userUpdate.setAge(55);
        userUpdate.setId(userRepository.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getId());

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(userUpdate)))
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        UserEntity user = userRepository.findById(userUpdate.getId()).orElseThrow();
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userUpdate.getFirstName()).isEqualTo(user.getFirstName());
            s.assertThat(userUpdate.getSecondName()).isEqualTo(user.getSecondName());
            s.assertThat(userUpdate.getAge()).isEqualTo(user.getAge());
        });
    }

    @Test
    //@Disabled
    void deleteUserTest() throws Exception {
        //pre-condition
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setSecondName("Second Name");
        userEntity.setAge(50);
        userRepository.saveAndFlush(userEntity);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + userEntity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .DELETE()
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userRepository.findById(userEntity.getId())).isEmpty();
        });
    }
}
