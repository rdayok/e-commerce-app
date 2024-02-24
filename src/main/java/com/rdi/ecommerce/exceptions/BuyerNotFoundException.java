package com.rdi.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class BuyerNotFoundException extends ECommerceException {
    public BuyerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
