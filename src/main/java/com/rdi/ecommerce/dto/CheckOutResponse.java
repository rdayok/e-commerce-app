package com.rdi.ecommerce.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutResponse {
    private Long orderId;
    private String paymentReference;
}
