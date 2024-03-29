package com.gyarmati.ponteexercisebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDto {
    private Long id;
    private String name;
    private String email;
    private String motherName;
    private String birthDate;
    private String socialSecurityNumber;
    private String taxIdentificationNumber;
    private List<String> rolesList;
}
