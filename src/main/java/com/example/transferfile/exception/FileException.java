package com.example.transferfile.exception;

public class FileException extends RuntimeException {

    public FileException(String message) {
        super(message);
    }

    public static FileException fileNotFound() {
        return new FileException("File not found");
    }
}
