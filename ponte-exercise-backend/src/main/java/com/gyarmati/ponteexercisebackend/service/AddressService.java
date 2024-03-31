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
        /*
        * Megnézzük hogy a beérkező AddressUpdateDto-nak az id listája ugyan akkora-e mint az Id-k alapján kikért Addressek listájának a mérete.
        * Ha nem ugyan akkora akkor olyan id is jött amilyen id-val nincs Address a db-ben.
        */
        if (addressIdList.size() != byAddressIdIn.size()) {
            throw new AddressNotFoundException();
        }
        return byAddressIdIn;
    }

    public void updateAddresses(List<Address> addressList, List<AddressUpdateDto> addressUpdateDtoList) {
        /*
         * Itt azért iterálok rajtuk végig és végzem el az if vizsgálatot id-ra hogy biztosan az ugyan azzal az id-val rendelkező Address-t
         * updatelem az AddressUpdateDto-val
         */
        for (Address address : addressList) {
            for (AddressUpdateDto addressUpdateDto : addressUpdateDtoList) {
                if (address.getId().equals(addressUpdateDto.getId())) {
                    updateValuesForAddress(address, addressUpdateDto);
                }
            }
        }
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

    private void updateValuesForAddress(Address address, AddressUpdateDto addressUpdateDto) {
        address.setCity(addressUpdateDto.getCity());
        address.setStreet(addressUpdateDto.getStreet());
        address.setZipCode(addressUpdateDto.getZipCode());
        address.setHouseNumber(address.getHouseNumber());
    }
}
