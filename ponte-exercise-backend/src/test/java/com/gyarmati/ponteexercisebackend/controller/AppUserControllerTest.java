package com.gyarmati.ponteexercisebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyarmati.ponteexercisebackend.domain.Role;
import com.gyarmati.ponteexercisebackend.dto.*;
import com.gyarmati.ponteexercisebackend.service.AppUserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private UserUpdateDto userUpdateDto;

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

        userUpdateDto = UserUpdateDto.builder()
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .password("Peter1234")
                .birthDate("2000-01-01")
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .build();

        userLoginDto = UserLoginDto.builder()
                .name("Peter")
                .password("Peter1234")
                .build();
    }

    @Test
    public void registerAppUser_returnsCreated() throws Exception {
        when(appUserService.register(userRegisterDto)).thenReturn(userDetailsDto);

        ResultActions response = mvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(userDetailsDto.getName())));
    }

    @Test
    public void registerAppUserByAdmin_returnsCreated() throws Exception {
        doNothing().when(appUserService).registerAppUserByAdmin(userRegisterByAdminDto);

        ResultActions response = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterByAdminDto)));

        response.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void deleteAppUser_returnsNoContent() throws Exception {
        doNothing().when(appUserService).delete("Peter");

        ResultActions response = mvc.perform(delete("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterByAdminDto)));

        response.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void updateAppUser_returnsOk() throws Exception {
        when(appUserService.update("Peter", userUpdateDto)).thenReturn(userDetailsDto);

        ResultActions response = mvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateDto)));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getAllAppUserWithPage_returnsOk() throws Exception {
        when(appUserService.listUserWithPage(0, 2)).thenReturn(List.of(userDetailsDto));

        ResultActions response = mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNo", "0")
                        .param("pageSize", "2"));

        response.andExpect(status().isOk());
    }
}
