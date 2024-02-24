package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class MerchantIsNotOwnerOfStoreException extends ECommerceException {
    public MerchantIsNotOwnerOfStoreException(String message) {
        super(message, FORBIDDEN);
    }
}
