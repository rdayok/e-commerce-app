package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductResponse {
    private Long id;
    private StoreResponse store;
}
