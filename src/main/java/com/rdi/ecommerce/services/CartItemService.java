package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.dto.AddCartItemRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CartItemDetails;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.CannotTakeOutCartItemThatDoesNotExistInYOurCartException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

import java.util.List;

public interface CartItemService {
    ApiResponse<?> addCartItem(AddCartItemRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, BuyerNotFoundException;

    ApiResponse<?> removeCartItem(AddCartItemRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, CannotTakeOutCartItemThatDoesNotExistInYOurCartException;

    List<CartItem> findAllCartItemBuyCartId(Long cartId);

    List<CartItemDetails> getAllCartItems(Long cartId);

    void clearCartItemsFromCart(List<CartItem> cartItems);
}
