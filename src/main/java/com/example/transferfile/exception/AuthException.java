package com.example.transferfile.exception;

public class AuthException extends Exception {

    public AuthException(String message) {
        super(message);
    }

    public static AuthException alreadyLoggedIn() {
        return new AuthException("User is already logged in");
    }

    public static AuthException usernameTaken() {
        return new AuthException("Username is taken");
    }
}
