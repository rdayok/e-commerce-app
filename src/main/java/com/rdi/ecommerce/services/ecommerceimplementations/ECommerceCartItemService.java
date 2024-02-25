package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.data.repository.CartItemRepository;
import com.rdi.ecommerce.dto.AddToCartRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.CannotTakeOutCartItemThatDoesNotExistInYOurCartException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.*;
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
    private final BuyerService buyerService;

    @Override
    @Transactional
    public ApiResponse<?> addCartItem(AddToCartRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, BuyerNotFoundException {

        Long productId = addToCartRequest.getProductId();
        Long buyerId = addToCartRequest.getBuyerId();
        Product gottenProduct = productService.getProductBy(productId);
        ProductInventory productInventory = gottenProduct.getProductInventory();
        Long productInventoryId = productInventory.getId();
        ApiResponse<?> reservationResponse = inventoryService.reserveProductBy(productInventoryId);
        Cart foundCart = cartService.findByBuyerId(buyerId);
        Long cartId = foundCart.getId();
        CartItem foundCartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId);
        if(foundCartItem == null){
            System.out.println("i came here man ...");
            foundCartItem = new CartItem();
            foundCartItem.increaseItemQuantity();
            foundCartItem.setProduct(gottenProduct);
            foundCartItem.setCart(foundCart);
        }else foundCartItem.increaseItemQuantity();
        CartItem savedCart = cartItemRepository.save(foundCartItem);
        System.out.println(savedCart);
        return new ApiResponse<>("SUCCESSFUL");
    }

    @Override
    @Transactional
    public ApiResponse<?> removeCartItem(AddToCartRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, CannotTakeOutCartItemThatDoesNotExistInYOurCartException {

        Long productId = addToCartRequest.getProductId();
        Long buyerId = addToCartRequest.getBuyerId();
        Product gottenProduct = productService.getProductBy(productId);
        ProductInventory productInventory = gottenProduct.getProductInventory();
        Long productInventoryId = productInventory.getId();
        ApiResponse<?> reservationResponse = inventoryService.returnReserveProductBy(productInventoryId);
        Cart foundCart = cartService.findByBuyerId(buyerId);
        Long cartId = foundCart.getId();
        CartItem foundCartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId);
        if(foundCartItem == null) throw new CannotTakeOutCartItemThatDoesNotExistInYOurCartException(
                    "You cannot reduce item that is not in your cart"
            );
        foundCartItem.decreaseItemQuantity();
        CartItem savedCart = cartItemRepository.save(foundCartItem);
        System.out.println(savedCart);
        return new ApiResponse<>("SUCCESSFUL");
    }
}
