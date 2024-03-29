package com.gyarmati.ponteexercisebackend.exceptionhandling;

public class BothEmailAndPhoneNumberCantBeEmptyException extends RuntimeException {
    public BothEmailAndPhoneNumberCantBeEmptyException(String message) {
        super(message);
    }
}
