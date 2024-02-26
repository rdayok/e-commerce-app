package com.rdi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CheckOutResponse {
    private Long orderId;
    private String paymentReference;
}
