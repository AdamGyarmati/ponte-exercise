package com.gyarmati.ponteexercisebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberRegisterDto {
    @NotNull
    @NotBlank
    private String phoneNumber;
}
