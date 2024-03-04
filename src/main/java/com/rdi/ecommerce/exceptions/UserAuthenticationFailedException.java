package com.rdi.ecommerce.exceptions;

import org.springframework.security.core.Authentication;

public class UserAuthenticationFailedException extends RuntimeException {
    public UserAuthenticationFailedException(String message) {
        super(message);
    }
}
