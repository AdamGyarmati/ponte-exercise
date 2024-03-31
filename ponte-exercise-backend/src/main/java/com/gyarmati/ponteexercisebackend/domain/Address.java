package com.gyarmati.ponteexercisebackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;

    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public Address(String zipCode, String city, String street, String houseNumber, AppUser appUser) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.appUser = appUser;
    }
}
