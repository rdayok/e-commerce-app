package com.rdi.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class StoreNotFoundException extends ECommerceException {
    public StoreNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
