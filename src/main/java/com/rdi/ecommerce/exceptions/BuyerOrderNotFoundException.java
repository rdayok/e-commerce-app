package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BuyerOrderNotFoundException extends ECommerceException {

    public BuyerOrderNotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
