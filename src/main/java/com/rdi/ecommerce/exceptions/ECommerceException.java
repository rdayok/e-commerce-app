package com.rdi.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class ECommerceException extends Throwable {
    private HttpStatus httpStatus;


    public ECommerceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ECommerceException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ECommerceException(Throwable throwable) {
        super(throwable);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
