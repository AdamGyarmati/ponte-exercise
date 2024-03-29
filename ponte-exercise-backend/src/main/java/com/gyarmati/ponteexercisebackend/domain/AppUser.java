package com.gyarmati.ponteexercisebackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "tax_identification_number")
    private String taxIdentificationNumber;

    @OneToMany(mappedBy = "appUser")
    private List<Address> addressList;

    @OneToMany(mappedBy = "appUser")
    private List<PhoneNumber> phoneNumberList;
}
