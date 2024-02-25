package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRestockRequest {
    private Long productId;
    private Long merchantId;
    private Integer productQuantity;
}
