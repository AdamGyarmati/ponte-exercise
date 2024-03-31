package com.gyarmati.ponteexercisebackend.repository;

import com.gyarmati.ponteexercisebackend.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByName(String username);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
