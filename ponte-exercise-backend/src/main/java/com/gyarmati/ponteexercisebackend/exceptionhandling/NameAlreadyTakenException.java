package com.gyarmati.ponteexercisebackend.exceptionhandling;

public class NameAlreadyTakenException extends RuntimeException {
    private final String name;
    public NameAlreadyTakenException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
