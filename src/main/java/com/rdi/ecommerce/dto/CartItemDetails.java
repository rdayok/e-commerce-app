package com.rdi.ecommerce.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CartItemDetails {
    private Long id;
    private Integer itemQuantity;
    private ProductResponse product;
    private CartResponse cart;
}
