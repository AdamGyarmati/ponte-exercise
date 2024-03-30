package com.gyarmati.ponteexercisebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberUpdateDto {
    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    private String phoneNumber;
}
