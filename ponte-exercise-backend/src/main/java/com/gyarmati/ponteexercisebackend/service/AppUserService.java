package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.*;
import com.gyarmati.ponteexercisebackend.dto.UserDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterDto;
import com.gyarmati.ponteexercisebackend.exceptionhandling.BothEmailAndPhoneNumberCantBeEmptyException;
import com.gyarmati.ponteexercisebackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByName(username);

        String[] roles = appUser.getAppUserRoleList().stream()
                .map(appUserRole -> appUserRole.getRole().toString()).toArray(String[]::new);

        return User.builder()
                .username(appUser.getName())
                .authorities(AuthorityUtils.createAuthorityList(roles))
                .password(appUser.getPassword())
                .build();
    }

    public UserDetailsDto register(UserRegisterDto userRegisterDto) {
        AppUser appUser = mapUserRegisterDtoToAppUser(userRegisterDto);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return mapAppUserToUserDetailsDto(savedAppUser);
    }

    private UserDetailsDto mapAppUserToUserDetailsDto(AppUser savedAppUser) {
        return UserDetailsDto.builder()
                .id(savedAppUser.getId())
                .name(savedAppUser.getName())
                .email(savedAppUser.getEmail())
                .birthDate(savedAppUser.getBirthDate().toString())
                .taxIdentificationNumber(savedAppUser.getTaxIdentificationNumber())
                .socialSecurityNumber(savedAppUser.getSocialSecurityNumber())
                .motherName(savedAppUser.getMotherName())
                .rolesList(savedAppUser.getAppUserRoleList().stream()
                        .map(role -> role.getRole().toString()).collect(Collectors.toList()))
                .build();
    }

    private AppUser mapUserRegisterDtoToAppUser(UserRegisterDto userRegisterDto) {
        if ((userRegisterDto.getEmail().isBlank() || userRegisterDto.getEmail() == null) &&
                (userRegisterDto.getPhoneNumberRegisterDto().getPhoneNumber().isBlank()
                        || userRegisterDto.getPhoneNumberRegisterDto().getPhoneNumber() == null)) {
            throw new BothEmailAndPhoneNumberCantBeEmptyException("Both Email and Phone Number can't be empty!");
        }

        AppUser appUser = AppUser.builder()
                .name(userRegisterDto.getName())
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .birthDate(LocalDate.parse(userRegisterDto.getBirthDate()))
                .motherName(userRegisterDto.getMotherName())
                .socialSecurityNumber(userRegisterDto.getSocialSecurityNumber())
                .taxIdentificationNumber(userRegisterDto.getTaxIdentificationNumber())
                .build();

        setAppUserAddressList(userRegisterDto, appUser);
        setAppUserPhoneNumber(userRegisterDto, appUser);
        setAppUserRoles(appUser);

        return appUser;
    }

    private void setAppUserRoles(AppUser appUser) {
        appUser.setAppUserRoleList(List.of(new AppUserRole(Role.ROLE_USER, appUser)));
    }

    private void setAppUserPhoneNumber(UserRegisterDto userRegisterDto, AppUser appUser) {
        appUser.setPhoneNumberList(List.of(
                new PhoneNumber(userRegisterDto.getPhoneNumberRegisterDto().getPhoneNumber(), appUser)));
    }

    private void setAppUserAddressList(UserRegisterDto userRegisterDto, AppUser appUser) {
        appUser.setAddressList(userRegisterDto.getAddressRegisterDtoList().stream()
                .map(addressDto ->
                        new Address(addressDto.getZipCode(),
                                addressDto.getCity(),
                                addressDto.getStreet(),
                                addressDto.getHouseNumber(),
                                appUser))
                .collect(Collectors.toList()));
    }

    public UserDetailsDto getUserByName(String name) {
        AppUser appUser = appUserRepository.findByName(name);
        return mapAppUserToUserDetailsDto(appUser);
    }
}
