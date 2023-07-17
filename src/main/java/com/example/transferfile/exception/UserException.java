package com.example.transferfile.exception;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public static UserException userNotFound() {
        return new UserException("User not found");
    }

    public static UserException userNotActive() {
        return new UserException("User is not active");
    }

    public static UserException isAlreadyActive() {
        return new UserException("User is already active");
    }
}
