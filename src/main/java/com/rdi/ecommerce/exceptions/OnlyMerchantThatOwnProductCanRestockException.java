package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class OnlyMerchantThatOwnProductCanRestockException extends ECommerceException {
    public OnlyMerchantThatOwnProductCanRestockException(String message) {
        super(message, FORBIDDEN);
    }
}
