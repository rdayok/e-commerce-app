package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AddressNotFoundException extends ECommerceException {
    public AddressNotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
