package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.dto.AddToCartRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CartRequest;
import com.rdi.ecommerce.dto.CartResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

public interface CartService {

    CartResponse createCart(Long buyerId) throws BuyerNotFoundException;

    Cart findByBuyerId(Long buyerId);
}
