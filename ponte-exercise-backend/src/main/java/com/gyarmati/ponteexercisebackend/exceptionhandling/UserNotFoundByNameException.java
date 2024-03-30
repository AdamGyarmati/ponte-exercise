package com.gyarmati.ponteexercisebackend.exceptionhandling;

public class UserNotFoundByNameException extends RuntimeException {
    private final String name;
    public UserNotFoundByNameException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
