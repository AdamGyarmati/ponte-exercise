package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.PhoneNumber;
import com.gyarmati.ponteexercisebackend.dto.PhoneNumberDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.PhoneNumberUpdateDto;
import com.gyarmati.ponteexercisebackend.exceptionhandling.PhoneNumberNotFoundByIdException;
import com.gyarmati.ponteexercisebackend.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public List<PhoneNumber> findPhoneNumbersById(List<Long> phoneNumberIdList) {
        List<PhoneNumber> byPhoneNumbersIdIn = phoneNumberRepository.findByIdIn(phoneNumberIdList);
        /*
         * Megnézzük hogy a beérkező PhoneNumberUpdateDto-nak az id listája ugyan akkora-e mint az Id-k alapján kikért PhoneNumber-ek listájának a mérete.
         * Ha nem ugyan akkora akkor olyan id is jött amilyen id-val nincs PhoneNumber a db-ben.
         */
        if (phoneNumberIdList.size() != byPhoneNumbersIdIn.size()) {
            throw new PhoneNumberNotFoundByIdException();
        }
        return byPhoneNumbersIdIn;
    }



    public PhoneNumberDetailsDto mapPhoneNumberToPhoneNumberDetailsDto(PhoneNumber phoneNumber) {
        return PhoneNumberDetailsDto.builder()
                .id(phoneNumber.getId())
                .phoneNumber(phoneNumber.getPhoneNumber())
                .build();
    }

    public void updatePhoneNumber(List<PhoneNumber> phoneNumbers, List<PhoneNumberUpdateDto> phoneNumberUpdateDtoList) {
        /*
         * Itt azért iterálok rajtuk végig és végzem el az if vizsgálatot id-ra hogy biztosan az ugyan azzal az id-val rendelkező PhoneNumbert-t
         * updatelem az PhoneNumberUpdateDto-val
         */
        for (PhoneNumber phoneNumber : phoneNumbers) {
            for (PhoneNumberUpdateDto phoneNumberUpdateDto : phoneNumberUpdateDtoList) {
                if (phoneNumber.getId().equals(phoneNumberUpdateDto.getId())) {
                    phoneNumber.setPhoneNumber(phoneNumberUpdateDto.getPhoneNumber());
                }
            }
        }
    }
}
