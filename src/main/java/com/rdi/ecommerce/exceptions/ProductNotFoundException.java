package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ProductNotFoundException extends ECommerceException {
    public ProductNotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
