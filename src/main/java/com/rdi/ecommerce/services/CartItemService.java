package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.AddToCartRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.CannotTakeOutCartItemThatDoesNotExistInYOurCartException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

public interface CartItemService {
    ApiResponse<?> addCartItem(AddToCartRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, BuyerNotFoundException;

    ApiResponse<?> removeCartItem(AddToCartRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, CannotTakeOutCartItemThatDoesNotExistInYOurCartException;
}
