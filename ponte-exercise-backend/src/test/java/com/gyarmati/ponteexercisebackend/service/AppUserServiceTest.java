package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.AppUser;
import com.gyarmati.ponteexercisebackend.domain.AppUserRole;
import com.gyarmati.ponteexercisebackend.domain.Role;
import com.gyarmati.ponteexercisebackend.dto.*;
import com.gyarmati.ponteexercisebackend.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser appUser;

    private UserRegisterDto userRegisterDto;

    private UserDetailsDto userDetailsDto;

    private UserRegisterByAdminDto userRegisterByAdminDto;

    @BeforeEach
    public void init() {
        appUser = AppUser.builder()
                .id(1L)
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .password("Peter1234")
                .birthDate(LocalDate.parse("2000-01-01"))
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .build();
        appUser.setAppUserRoleList(List.of(new AppUserRole(Role.ROLE_ADMIN, appUser)));

        userRegisterDto = UserRegisterDto.builder()
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
    }

    @Test
    public void registerUser_returnsUserDetailsDto() {
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        UserDetailsDto userDetailsDto1 = appUserService.register(userRegisterDto);

        assertThat(userDetailsDto1).isNotNull();
    }

    @Test
    public void getUserByName_returnsUserDetailsDto() {
        String name = "Peter";
        when(appUserRepository.findByName(name)).thenReturn(appUser);

        UserDetailsDto userDetailsDto1 = appUserService.getUserByName(name);

        assertThat(userDetailsDto1).isNotNull();
    }

    @Test
    public void registerUserByAdmin_void() {
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        appUserService.registerAppUserByAdmin(userRegisterByAdminDto);

        assertThat(appUser).isNotNull();
    }

    @Test
    public void deleteUser_returnsVoid() {
        when(appUserRepository.findByName(appUser.getName())).thenReturn(appUser);

        appUserService.delete(appUser.getName());

        verify(appUserRepository, times(1)).delete(appUser);
    }
}
