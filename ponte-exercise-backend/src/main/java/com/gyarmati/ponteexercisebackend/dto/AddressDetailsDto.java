package com.gyarmati.ponteexercisebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDetailsDto {
    private Long id;

    private String zipCode;

    private String city;

    private String street;

    private String houseNumber;

}
