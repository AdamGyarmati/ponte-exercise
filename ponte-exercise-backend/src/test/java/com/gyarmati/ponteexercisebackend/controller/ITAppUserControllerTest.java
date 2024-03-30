package com.gyarmati.ponteexercisebackend.controller;

import com.gyarmati.ponteexercisebackend.dto.AddressRegisterDto;
import com.gyarmati.ponteexercisebackend.dto.PhoneNumberRegisterDto;
import com.gyarmati.ponteexercisebackend.dto.UserDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterDto;
import com.gyarmati.ponteexercisebackend.service.AppUserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ITAppUserControllerTest {

    @LocalServerPort
    private Integer port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private AppUserService appUserService;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        restTemplate = new RestTemplate();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/users");
    }

    @Test
    public void testSaveAppUser() throws Exception {
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .password("Peter1234")
                .birthDate("2000-01-01")
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .addressRegisterDtoList(List.of(new AddressRegisterDto("1111", "Budapest", "Babér", "23")))
                .phoneNumberRegisterDto(new PhoneNumberRegisterDto("11111111"))
                .build();

        UserDetailsDto userDetailsDto1 = restTemplate.postForObject(baseUrl + "/register", userRegisterDto, UserDetailsDto.class);
        assertEquals("Parker", userDetailsDto1.getName());
    }
}
