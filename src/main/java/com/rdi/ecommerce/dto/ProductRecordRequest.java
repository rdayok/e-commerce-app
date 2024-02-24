package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRecordRequest {
    private Long productId;
    private Long merchantId;
    private int productQuantity;
}
