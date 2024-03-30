package com.gyarmati.ponteexercisebackend.service;

import com.gyarmati.ponteexercisebackend.domain.Address;
import com.gyarmati.ponteexercisebackend.dto.AddressDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.AddressUpdateDto;
import com.gyarmati.ponteexercisebackend.exceptionhandling.AddressNotFoundException;
import com.gyarmati.ponteexercisebackend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public List<Address> findAddressesById(List<Long> addressIdList) {
        List<Address> byAddressIdIn = addressRepository.findByIdIn(addressIdList);
        if (addressIdList.size() != byAddressIdIn.size()) {
            throw new AddressNotFoundException();
        }
        return byAddressIdIn;
    }

    public void updateAddresses(List<Address> addressList, List<AddressUpdateDto> addressUpdateDtoList) {
        for (Address address : addressList) {
            for (AddressUpdateDto addressUpdateDto : addressUpdateDtoList) {
                if (address.getId().equals(addressUpdateDto.getId())) {
                    updateValuesForAddress(address, addressUpdateDto);
                }
            }
        }
    }

    private void updateValuesForAddress(Address address, AddressUpdateDto addressUpdateDto) {
        address.setCity(addressUpdateDto.getCity());
        address.setStreet(addressUpdateDto.getStreet());
        address.setZipCode(addressUpdateDto.getZipCode());
        address.setHouseNumber(address.getHouseNumber());
    }

    public AddressDetailsDto mapAddressToAddressDetailsDto(Address address) {
        return AddressDetailsDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .houseNumber(address.getHouseNumber())
                .zipCode(address.getZipCode())
                .street(address.getStreet())
                .build();
    }
}
