package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CannotRestockAnotherMerchantProduct extends ECommerceException {
    public CannotRestockAnotherMerchantProduct(String message) {
        super(message, FORBIDDEN);
    }
}
