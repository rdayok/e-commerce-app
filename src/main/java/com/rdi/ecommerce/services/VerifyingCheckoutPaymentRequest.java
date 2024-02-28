package com.rdi.ecommerce.services;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyingCheckoutPaymentRequest {
    private Long buyerOrderId;
    private Long buyerId;
}
