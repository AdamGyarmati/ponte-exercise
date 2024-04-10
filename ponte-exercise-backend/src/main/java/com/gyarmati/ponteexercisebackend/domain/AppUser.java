package com.gyarmati.ponteexercisebackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
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

    @OneToMany(mappedBy = "appUser", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Address> addressList;

    @OneToMany(mappedBy = "appUser", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<PhoneNumber> phoneNumberList;

    @OneToMany(mappedBy = "appUser", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AppUserRole> appUserRoleList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (AppUserRole appUserRole : appUserRoleList) {
            roles.add(new SimpleGrantedAuthority(appUserRole.getRole().getRoleName()));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
