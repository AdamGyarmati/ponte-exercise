package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.PhoneNumber;
import com.gyarmati.ponteexercisebackend.dto.PhoneNumberDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.PhoneNumberUpdateDto;
import com.gyarmati.ponteexercisebackend.exceptionhandling.PhoneNumberNotFoundByIdException;
import com.gyarmati.ponteexercisebackend.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public PhoneNumber findPhoneNumberById(Long id) {
        return phoneNumberRepository.findById(id).orElseThrow(() -> new PhoneNumberNotFoundByIdException(id));
    }

    public void updatePhoneNumber(PhoneNumber phoneNumber, PhoneNumberUpdateDto phoneNumberUpdateDto) {
        phoneNumber.setPhoneNumber(phoneNumberUpdateDto.getPhoneNumber());
    }

    public PhoneNumberDetailsDto mapPhoneNumberToPhoneNumberDetailsDto(PhoneNumber phoneNumber) {
        return PhoneNumberDetailsDto.builder()
                .id(phoneNumber.getId())
                .phoneNumber(phoneNumber.getPhoneNumber())
                .build();
    }
}
