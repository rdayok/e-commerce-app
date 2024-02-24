package com.rdi.ecommerce.dto;

import com.rdi.ecommerce.data.model.Cart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BuyerRegisterResponse {
    private Long id;
    private CartResponse cart;
}
