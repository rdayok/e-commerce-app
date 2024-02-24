package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoreRequest {
    private Long merchantId;
    private String storeName;
}
