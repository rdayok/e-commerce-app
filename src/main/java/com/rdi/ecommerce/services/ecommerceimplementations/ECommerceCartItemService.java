package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.data.repository.CartItemRepository;
import com.rdi.ecommerce.dto.AddToCartRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.CartItemService;
import com.rdi.ecommerce.services.CartService;
import com.rdi.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceCartItemService implements CartItemService {

    private final ProductService productService;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    @Override
    public ApiResponse<?> addCartItem(AddToCartRequest addToCartRequest) throws ProductNotFoundException {
        Long productId = addToCartRequest.getProductId();
        Product gottenProduct = productService.getProductBy(productId);
        Long buyerId = addToCartRequest.getBuyerId();
        Cart foundCart = cartService.findByBuyerId(buyerId);
        CartItem cartItem = new CartItem();
        cartItem.setItemQuantity(1);
        cartItem.setProduct(gottenProduct);
        cartItem.setCart(foundCart);
        CartItem savedCart = cartItemRepository.save(cartItem);
        ApiResponse<String> response = new ApiResponse<>();
        System.out.println(savedCart);
        response.setMessage(String.format("successful %d", savedCart.getId()));
        return response;

    }
}
