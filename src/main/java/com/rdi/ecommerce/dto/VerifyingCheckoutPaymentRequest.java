package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyingCheckoutPaymentRequest {
    private Long buyerOrderId;
    private Long buyerId;
}
