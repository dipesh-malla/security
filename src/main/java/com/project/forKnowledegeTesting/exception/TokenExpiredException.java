package com.project.forKnowledegeTesting.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String tokenExpired) {
        super(tokenExpired);
    }
}
