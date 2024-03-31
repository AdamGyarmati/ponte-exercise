package com.gyarmati.ponteexercisebackend.repository;

import com.gyarmati.ponteexercisebackend.domain.AppUser;
import com.gyarmati.ponteexercisebackend.domain.AppUserRole;
import com.gyarmati.ponteexercisebackend.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser appUser;

    @BeforeEach
    public void init() {
        appUser = AppUser.builder()
                .name("Parker")
                .email("peter@gmail.com")
                .motherName("Peter Mama")
                .password("Peter1234")
                .birthDate(LocalDate.parse("2000-01-01"))
                .socialSecurityNumber("0000")
                .taxIdentificationNumber("0000")
                .build();
        appUser.setAppUserRoleList(List.of(new AppUserRole(Role.ROLE_ADMIN, appUser)));
    }

    @Test
    public void findByName_returnAppUser() {
        appUserRepository.save(appUser);
        AppUser foundAppUser = appUserRepository.findByName("Parker");

        assertThat(foundAppUser).isNotNull();
        assertThat(foundAppUser.getName()).isEqualTo("Parker");
    }

    @Test
    public void existsByName_returnTrue() {
        appUserRepository.save(appUser);
        boolean result = appUserRepository.existsByName("Parker");
        assertThat(result).isTrue();
    }

    @Test
    public void existsByEmail_returnTrue() {
        appUserRepository.save(appUser);
        boolean result = appUserRepository.existsByEmail("peter@gmail.com");
        assertThat(result).isTrue();
    }
}
