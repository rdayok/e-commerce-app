package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequest {
    private Long productId;
    private Long buyerId;
    private int quantityOfProduct;
}
