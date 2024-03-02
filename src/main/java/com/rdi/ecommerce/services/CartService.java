package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.dto.CartResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;

public interface CartService {

    CartResponse createCart(Long buyerId) throws BuyerNotFoundException;

    Cart findByBuyerId(Long buyerId);
}
