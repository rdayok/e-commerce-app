package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PaymentRequest {
    private String email;
    private BigDecimal amount;
}
