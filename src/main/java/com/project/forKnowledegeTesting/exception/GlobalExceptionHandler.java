package com.project.forKnowledegeTesting.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.directory.AttributeInUseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AttributeInUseException.class)
    public ResponseEntity<?> attributeInUseException(AttributeInUseException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> tokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> authenticationFailedException(AuthenticationFailedException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

}
