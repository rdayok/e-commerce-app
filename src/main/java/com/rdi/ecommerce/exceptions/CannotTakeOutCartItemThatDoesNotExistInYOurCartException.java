package com.rdi.ecommerce.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CannotTakeOutCartItemThatDoesNotExistInYOurCartException extends ECommerceException{
    public CannotTakeOutCartItemThatDoesNotExistInYOurCartException(String message) {
        super(message, FORBIDDEN);
    }
}
