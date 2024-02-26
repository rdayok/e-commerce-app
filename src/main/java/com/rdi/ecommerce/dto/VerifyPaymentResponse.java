package com.rdi.ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class VerifyPaymentResponse {
    private boolean status;
    private String message;
    private VerifyPaymentData data;
}
