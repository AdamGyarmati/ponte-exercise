package com.gyarmati.ponteexercisebackend.repository;

import com.gyarmati.ponteexercisebackend.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    List<PhoneNumber> findByIdIn(List<Long> phoneNumberIdList);
}
