package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ProductInventoryNotFoundException extends ECommerceException {
    public ProductInventoryNotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
