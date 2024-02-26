package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class PaymentRequest {
    private String email;
    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        int TEN = 100;
        this.amount = amount.multiply(BigDecimal.valueOf(TEN));
    }
}
