package com.extensions.services.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException{
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
