package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.*;
import com.gyarmati.ponteexercisebackend.dto.*;
import com.gyarmati.ponteexercisebackend.exceptionhandling.BothEmailAndPhoneNumberCantBeEmptyException;
import com.gyarmati.ponteexercisebackend.exceptionhandling.UserNotFoundByNameException;
import com.gyarmati.ponteexercisebackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AddressService addressService, PhoneNumberService phoneNumberService, @Lazy PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.addressService = addressService;
        this.phoneNumberService = phoneNumberService;
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

    public UserDetailsDto getUserByName(String name) {
        AppUser appUser = appUserRepository.findByName(name);
        return mapAppUserToUserDetailsDto(appUser);
    }

    public void registerAppUserByAdmin(UserRegisterByAdminDto userRegisterByAdminDto) {
        AppUser appUser = AppUser.builder()
                .name(userRegisterByAdminDto.getName())
                .password(passwordEncoder.encode(userRegisterByAdminDto.getPassword()))
                .build();
        setAppUserRoles(appUser);
        appUserRepository.save(appUser);
    }

    public void delete(String name) {
        AppUser appUser = findByName(name);
        appUserRepository.delete(appUser);
    }

    public UserDetailsDto update(String name, UserUpdateDto userUpdateDto) {
        if ((userUpdateDto.getEmail().isBlank() || userUpdateDto.getEmail() == null) &&
                (userUpdateDto.getPhoneNumberUpdateDto().getPhoneNumber().isBlank()
                        || userUpdateDto.getPhoneNumberUpdateDto().getPhoneNumber() == null)) {
            throw new BothEmailAndPhoneNumberCantBeEmptyException("Both Email and Phone Number can't be empty!");
        }
        AppUser appUser = findByName(name);
        List<Long> addressIdList = userUpdateDto.getAddressUpdateDtoList().stream()
                .map(AddressUpdateDto::getId)
                .toList();
        List<Address> address = addressService.findAddressesById(addressIdList);
        PhoneNumber phoneNumber = phoneNumberService.findPhoneNumberById(userUpdateDto.getPhoneNumberUpdateDto().getId());

        addressService.updateAddresses(address, userUpdateDto.getAddressUpdateDtoList());
        phoneNumberService.updatePhoneNumber(phoneNumber, userUpdateDto.getPhoneNumberUpdateDto());

        updateValuesForAppUser(userUpdateDto, appUser);
        return mapAppUserToUserDetailsDto(appUser);
    }

    public List<UserDetailsDto> listUserWithPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<AppUser> appUserPage = appUserRepository.findAll(pageable);
        List<AppUser> appUserList = appUserPage.getContent();
        return appUserList.stream()
                .map(this::mapAppUserToUserDetailsDto)
                .collect(Collectors.toList());
    }

    private void updateValuesForAppUser(UserUpdateDto userUpdateDto, AppUser appUser) {
        appUser.setName(userUpdateDto.getName());
        appUser.setEmail(userUpdateDto.getEmail());
        appUser.setBirthDate(LocalDate.parse(userUpdateDto.getBirthDate()));
        appUser.setMotherName(userUpdateDto.getMotherName());
        appUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        appUser.setSocialSecurityNumber(userUpdateDto.getSocialSecurityNumber());
        appUser.setTaxIdentificationNumber(userUpdateDto.getTaxIdentificationNumber());
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
                .addressDetailsDtosList(savedAppUser.getAddressList().stream()
                        .map(addressService::mapAddressToAddressDetailsDto)
                        .collect(Collectors.toList()))
                .phoneNumberDetailsDtoList(savedAppUser.getPhoneNumberList().stream()
                        .map(phoneNumberService::mapPhoneNumberToPhoneNumberDetailsDto)
                        .collect(Collectors.toList()))
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

    private AppUser findByName(String name) {
        AppUser user = appUserRepository.findByName(name);
        if (user == null) {
            throw new UserNotFoundByNameException(name);
        }
        return user;
    }
}
