package com.gyarmati.ponteexercisebackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyarmati.ponteexercisebackend.domain.Role;
import com.gyarmati.ponteexercisebackend.dto.UserDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.UserLoginDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterByAdminDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterDto;
import com.gyarmati.ponteexercisebackend.service.AppUserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AppUserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegisterDto userRegisterDto;

    private UserDetailsDto userDetailsDto;

    private UserRegisterByAdminDto userRegisterByAdminDto;

    private UserLoginDto userLoginDto;

    @BeforeEach
    public void init() {
        userRegisterDto = UserRegisterDto.builder()
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .password("Peter1234")
                .birthDate("2000-01-01")
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .build();

        userDetailsDto = UserDetailsDto.builder()
                .id(1L)
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .birthDate("2000-01-01")
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .rolesList(List.of(Role.ROLE_ADMIN.toString()))
                .build();

        userRegisterByAdminDto = UserRegisterByAdminDto.builder()
                .name("Béla")
                .password("test1234")
                .build();

        userLoginDto = UserLoginDto.builder()
                .name("Peter")
                .password("Peter1234")
                .build();
    }

    @Test
    public void registerAppUser_ReturnsCreated() throws Exception {
        when(appUserService.register(userRegisterDto)).thenReturn(userDetailsDto);

        ResultActions response = mvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(userDetailsDto.getName())));
    }

    @Test
    public void registerAppUserByAdmin_ReturnsCreated() throws Exception {
        doNothing().when(appUserService).registerAppUserByAdmin(userRegisterByAdminDto);

        ResultActions response = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterByAdminDto)));

        response.andExpect(status().isCreated());
    }

}
