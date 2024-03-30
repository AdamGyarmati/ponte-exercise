package com.gyarmati.ponteexercisebackend.exceptionhandling;

public class PhoneNumberNotFoundByIdException extends RuntimeException {
    private final Long phoneNumberId;
    public PhoneNumberNotFoundByIdException(Long id) {
        this.phoneNumberId = id;
    }

    public Long getPhoneNumberId() {
        return phoneNumberId;
    }
}
