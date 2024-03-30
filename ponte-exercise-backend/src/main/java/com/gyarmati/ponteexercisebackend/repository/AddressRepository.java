package com.gyarmati.ponteexercisebackend.repository;

import com.gyarmati.ponteexercisebackend.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByIdIn(List<Long> addressIdList);
}
