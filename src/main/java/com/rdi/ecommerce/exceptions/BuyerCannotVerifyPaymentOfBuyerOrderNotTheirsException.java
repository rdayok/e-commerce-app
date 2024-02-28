package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException extends ECommerceException {
    public BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException(String message) {
        super(message, FORBIDDEN);
    }
}
