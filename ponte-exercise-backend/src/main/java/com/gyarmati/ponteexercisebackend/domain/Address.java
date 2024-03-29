package com.gyarmati.ponteexercisebackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
}
