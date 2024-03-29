package com.gyarmati.ponteexercisebackend.domain;

public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String roleName;

    Role(String role) {
        this.roleName = role;
    }

    public String getRoleName() {
        return roleName;
    }
}
