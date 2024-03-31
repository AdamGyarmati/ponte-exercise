package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.*;
import com.gyarmati.ponteexercisebackend.dto.*;
import com.gyarmati.ponteexercisebackend.exceptionhandling.BothEmailAndPhoneNumberCantBeEmptyException;
import com.gyarmati.ponteexercisebackend.exceptionhandling.EmailAlreadyTakenException;
import com.gyarmati.ponteexercisebackend.exceptionhandling.NameAlreadyTakenException;
import com.gyarmati.ponteexercisebackend.exceptionhandling.UserNotFoundByNameException;
import com.gyarmati.ponteexercisebackend.repository.AppUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private PhoneNumberService phoneNumberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser appUser;

    private UserRegisterDto userRegisterDto;

    private UserDetailsDto userDetailsDto;

    private UserRegisterByAdminDto userRegisterByAdminDto;

    private UserUpdateDto userUpdateDto;

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
        appUser.setAddressList(List.of(new Address("1234", "Bp", "Babér", "23", appUser)));
        appUser.setPhoneNumberList(List.of(new PhoneNumber(1L, "000", appUser)));

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

        userUpdateDto = UserUpdateDto.builder()
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .password("Peter1234")
                .birthDate("2000-01-01")
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .addressUpdateDtoList(
                        List.of(
                                new AddressUpdateDto(1L, "1234", "Bp", "Babér", "23")))
                .phoneNumberUpdateDto(new PhoneNumberUpdateDto(1L, "0000"))
                .build();
    }

    @Test
    public void registerUser_returnsUserDetailsDto() {
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        UserDetailsDto userDetailsDto1 = appUserService.register(userRegisterDto);

        assertThat(userDetailsDto1).isNotNull();
    }

    @Test
    public void registerUser_returnsNameAlreadyTakenException() {
        when(appUserRepository.existsByName(userRegisterDto.getName())).thenReturn(true);

        assertThrows(NameAlreadyTakenException.class, () -> appUserService.register(userRegisterDto));
    }

    @Test
    public void registerUser_returnsEmailAlreadyTakenException() {
        when(appUserRepository.existsByEmail(userRegisterDto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyTakenException.class, () -> appUserService.register(userRegisterDto));
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


    @Test
    public void updateUser_returnsUserDetailsDto() {
        when(appUserRepository.findByName("Peter")).thenReturn(appUser);
        when(addressService.findAddressesById(List.of(1L)))
                .thenReturn(List.of(
                        new Address("1234", "Bp", "Babér", "23", appUser)));

        when(phoneNumberService.findPhoneNumberById(1L))
                .thenReturn(new PhoneNumber(1L, "000", appUser));

        UserDetailsDto userDetailsDto1 = appUserService.update("Peter", userUpdateDto);

        assertThat(userDetailsDto1).isNotNull();
    }

    @Test
    public void updateUser_returnsBothEmailAndPhoneNumberCantBeEmptyException() {
        userUpdateDto.setEmail(null);
        userUpdateDto.getPhoneNumberUpdateDto().setPhoneNumber(null);
        assertThrows(BothEmailAndPhoneNumberCantBeEmptyException.class, () -> appUserService.update("Peter", userUpdateDto));
    }

    @Test
    public void updateUser_returnsUserNotFoundByNameException() {
        when(appUserRepository.findByName(userUpdateDto.getName())).thenReturn(null);

        assertThrows(UserNotFoundByNameException.class, () -> appUserService.update(userUpdateDto.getName(), userUpdateDto));
    }

    @Test
    public void listUserWithPage_returnsListOfUserDetailsDto() {
        Page<AppUser> appUserPage = Mockito.mock(Page.class);

        when(appUserRepository.findAll(Mockito.any(Pageable.class))).thenReturn(appUserPage);

        List<UserDetailsDto> userDetailsDtoList = appUserService.listUserWithPage(1, 2);

        assertThat(userDetailsDtoList).isNotNull();
    }
}
