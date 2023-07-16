package com.example.transferfile.exception;

import com.example.transferfile.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDetails> handle(Exception ex, WebRequest req, HttpStatus status) {
        String desc = req.getDescription(false);
        ErrorDetails details = new ErrorDetails(new Date(), ex.getMessage(), desc);
        return new ResponseEntity<>(details, status);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDetails> handle(AuthException ex, WebRequest req) {
        return handle(ex, req, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> handle(UserException ex, WebRequest req) {
        return handle(ex, req, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorDetails> handle(FileException ex, WebRequest req) {
        return handle(ex, req, HttpStatus.NOT_FOUND);
    }
}
