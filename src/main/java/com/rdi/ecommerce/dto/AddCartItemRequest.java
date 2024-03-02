package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCartItemRequest {
    private Long productId;
    private Long buyerId;
}
