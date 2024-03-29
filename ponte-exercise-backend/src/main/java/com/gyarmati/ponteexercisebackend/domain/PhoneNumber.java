package com.gyarmati.ponteexercisebackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_number")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public PhoneNumber(String phoneNumber, AppUser appUser) {
        this.phoneNumber = phoneNumber;
        this.appUser = appUser;
    }
}
