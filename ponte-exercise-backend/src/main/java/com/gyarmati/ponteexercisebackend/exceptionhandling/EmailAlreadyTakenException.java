package com.gyarmati.ponteexercisebackend.exceptionhandling;

public class EmailAlreadyTakenException extends RuntimeException {
    private final String email;
    public EmailAlreadyTakenException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
