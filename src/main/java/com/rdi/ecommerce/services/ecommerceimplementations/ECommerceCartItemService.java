package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.data.model.ProductInventory;
import com.rdi.ecommerce.data.repository.CartItemRepository;
import com.rdi.ecommerce.dto.AddToCartRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.CartItemService;
import com.rdi.ecommerce.services.CartService;
import com.rdi.ecommerce.services.InventoryService;
import com.rdi.ecommerce.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceCartItemService implements CartItemService {

    private final ProductService productService;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public ApiResponse<?> addCartItem(AddToCartRequest addToCartRequest) throws ProductNotFoundException, ProductInventoryNotFoundException {
        Long productId = addToCartRequest.getProductId();
        Product gottenProduct = productService.getProductBy(productId);
        Long buyerId = addToCartRequest.getBuyerId();
        ProductInventory productInventory = gottenProduct.getProductInventory();
        Long productInventoryId = productInventory.getId();
        ApiResponse<?> reservationResponse = inventoryService.reserveProductBy(productInventoryId);
        Cart foundCart = cartService.findByBuyerId(buyerId);
        CartItem cartItem = new CartItem();
        cartItem.setItemQuantity(1);
        cartItem.setProduct(gottenProduct);
        cartItem.setCart(foundCart);
        CartItem savedCart = cartItemRepository.save(cartItem);
        ApiResponse<?> response = new ApiResponse<>();
        System.out.println(savedCart);
        response.setMessage(String.format("successful %d", savedCart.getId()));
        return response;
    }
}
