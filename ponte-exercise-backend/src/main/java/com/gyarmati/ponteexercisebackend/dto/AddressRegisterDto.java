package com.gyarmati.ponteexercisebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRegisterDto {
    @NotNull
    @NotBlank
    private String zipCode;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    private String houseNumber;
}
