package com.gyarmati.ponteexercisebackend.exceptionhandling;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NameAlreadyTakenException extends RuntimeException {
    private final String name;
    public NameAlreadyTakenException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
