package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitialisePaymentRequest {
    private Long buyerId;
    private Long cartId;
}
