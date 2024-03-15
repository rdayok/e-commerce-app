package com.rdi.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ECommerceGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgumentsFiledExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMapped = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorsMapped.put(error.getField(), error.getDefaultMessage()));
        return errorsMapped;
    }

    @ExceptionHandler(ECommerceException.class)
    public ResponseEntity<Map<String, String>> handleECommerceException(ECommerceException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("Error ", exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(error);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleECommerceOtherException(RuntimeException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("Error ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
