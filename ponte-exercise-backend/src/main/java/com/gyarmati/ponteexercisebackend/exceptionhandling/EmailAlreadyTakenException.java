package com.gyarmati.ponteexercisebackend.exceptionhandling;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailAlreadyTakenException extends RuntimeException {
    private final String email;
    public EmailAlreadyTakenException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
