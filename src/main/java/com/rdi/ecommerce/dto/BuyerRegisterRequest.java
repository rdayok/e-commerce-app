package com.rdi.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuyerRegisterRequest {
    @Pattern(regexp = "(?:(?:(?:\\+?234(?:\\h1)?|01)\\h*)?(?:\\(\\d{3}\\)|\\d{3})|\\d{4})(?:\\W*\\d{3})?\\W*\\d{4}(?!\\d)",
            message = "Please enter a valid phone number")
    private String phoneNumber;
    @Valid
    private UserRegisterRequest userRegisterRequest;
}
