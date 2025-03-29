package com.project.forKnowledegeTesting.exception;

public class TokenExpiredException extends Throwable {
    public TokenExpiredException(String tokenExpired) {
        super(tokenExpired);
    }
}
