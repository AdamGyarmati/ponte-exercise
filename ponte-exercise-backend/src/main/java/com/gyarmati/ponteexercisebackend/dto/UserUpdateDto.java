package com.gyarmati.ponteexercisebackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String birthDate;

    @NotNull
    @NotBlank
    private String motherName;

    @NotNull
    @NotBlank
    private String socialSecurityNumber;

    @NotNull
    @NotBlank
    private String taxIdentificationNumber;

    private List<PhoneNumberUpdateDto> phoneNumberUpdateDtoList;

    private List<AddressUpdateDto> addressUpdateDtoList;
}
