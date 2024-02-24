package com.rdi.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class MerchantNotFoundException extends ECommerceException {
    public MerchantNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
