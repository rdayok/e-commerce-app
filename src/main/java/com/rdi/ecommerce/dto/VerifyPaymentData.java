package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class VerifyPaymentData {
    private String id;
    private String domain;
    private String status;
    private String reference;
    private BigDecimal amount;
    private String gateway_response;
    private String created_at;
    private VerifyPaymentCustomer customer;
    private VerifyPaymentPlan plan;
}
