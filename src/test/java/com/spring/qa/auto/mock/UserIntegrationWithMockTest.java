package com.spring.qa.auto.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.qa.auto.mock.controller.dto.User;
import com.spring.qa.auto.mock.repository.UserRepository;
import com.spring.qa.auto.mock.service.UserService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationWithMockTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private User user;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeAll
    void init() {
        user = new User();
        user.setAge(50);
        user.setFirstName("First Name");
        user.setSecondName("Second Name");
        Mockito.when(userService.getById(Mockito.anyInt())).thenReturn(user);
    }

    @Test
    void getUserTest() throws Exception {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + new Random().nextInt(5)))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        User userResponse = objectMapper.readValue(response.body(), User.class);
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(user.getFirstName()).isEqualTo(userResponse.getFirstName());
            s.assertThat(user.getSecondName()).isEqualTo(userResponse.getSecondName());
            s.assertThat(user.getAge()).isEqualTo(userResponse.getAge());
        });
    }

    @Test
    void deleteUserTest() throws Exception{

        int userIdForDelete = new Random().nextInt(5);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + userIdForDelete))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .DELETE()
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        Mockito.when(userService.getById(Mockito.anyInt())).thenReturn(null);
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userService.getById(userIdForDelete)).isNull();
        });
    }
}
