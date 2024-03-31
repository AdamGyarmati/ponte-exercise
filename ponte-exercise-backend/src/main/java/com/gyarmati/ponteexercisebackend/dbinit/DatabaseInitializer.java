package com.gyarmati.ponteexercisebackend.dbinit;

import com.gyarmati.ponteexercisebackend.domain.*;
import com.gyarmati.ponteexercisebackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Address address1 = Address.builder()
                .zipCode("1234")
                .city("Budapest")
                .street("Babér")
                .houseNumber("23")
                .build();

        Address address2 = Address.builder()
                .zipCode("5678")
                .city("Győr")
                .street("Csobogós")
                .houseNumber("10")
                .build();

        Address address3 = Address.builder()
                .zipCode("2222")
                .city("Szeged")
                .street("Nagymező")
                .houseNumber("2")
                .build();

        Address address4 = Address.builder()
                .zipCode("3333")
                .city("Győr")
                .street("Deák Feren")
                .houseNumber("1")
                .build();

        PhoneNumber phoneNumber1 = PhoneNumber.builder()
                .phoneNumber("06301234567")
                .build();

        PhoneNumber phoneNumber2 = PhoneNumber.builder()
                .phoneNumber("06207891234")
                .build();

        PhoneNumber phoneNumber3 = PhoneNumber.builder()
                .phoneNumber("06704351322")
                .build();

        PhoneNumber phoneNumber4 = PhoneNumber.builder()
                .phoneNumber("06202231567")
                .build();

        AppUserRole appUserRole1 = AppUserRole.builder()
                .role(Role.ROLE_ADMIN)
                .build();

        AppUserRole appUserRole2 = AppUserRole.builder()
                .role(Role.ROLE_USER)
                .build();

        AppUserRole appUserRole3 = AppUserRole.builder()
                .role(Role.ROLE_USER)
                .build();

        AppUser appUser1 = AppUser.builder()
                .name("John Doe")
                .email("johndoe@gmail.com")
                .password(passwordEncoder.encode("test1234"))
                .birthDate(LocalDate.parse("2000-01-01"))
                .motherName("Mother Doe")
                .socialSecurityNumber("012-345-678")
                .taxIdentificationNumber("123456")
                .addressList(List.of(address1, address2))
                .phoneNumberList(List.of(phoneNumber1, phoneNumber2))
                .appUserRoleList(List.of(appUserRole1, appUserRole2))
                .build();

        AppUser appUser2 = AppUser.builder()
                .name("Jane Doe")
                .email("janedoe@gmail.com")
                .password(passwordEncoder.encode("test1234"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .motherName("Mother Doe")
                .socialSecurityNumber("345-012-678")
                .taxIdentificationNumber("987654")
                .addressList(List.of(address3, address4))
                .phoneNumberList(List.of(phoneNumber3, phoneNumber4))
                .appUserRoleList(List.of(appUserRole3))
                .build();

        address1.setAppUser(appUser1);
        address2.setAppUser(appUser1);
        phoneNumber1.setAppUser(appUser1);
        phoneNumber2.setAppUser(appUser1);
        appUserRole1.setAppUser(appUser1);
        appUserRole2.setAppUser(appUser1);

        address3.setAppUser(appUser2);
        address4.setAppUser(appUser2);
        phoneNumber3.setAppUser(appUser2);
        phoneNumber4.setAppUser(appUser2);
        appUserRole3.setAppUser(appUser2);

        appUserRepository.save(appUser1);
        appUserRepository.save(appUser2);
    }
}
