package com.extensions.services.exceptions;

public class AccessDeniedGenericException extends RuntimeException{
    public AccessDeniedGenericException(String message) {
        super(message);
    }
}
