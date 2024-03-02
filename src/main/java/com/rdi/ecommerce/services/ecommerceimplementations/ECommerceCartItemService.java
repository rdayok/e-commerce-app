package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.data.repository.CartItemRepository;
import com.rdi.ecommerce.dto.AddCartItemRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CartItemDetails;
import com.rdi.ecommerce.exceptions.CannotTakeOutCartItemThatDoesNotExistInYOurCartException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ECommerceCartItemService implements CartItemService {

    private final ProductService productService;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final InventoryService inventoryService;
    private final BuyerService buyerService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ApiResponse<?> addCartItem(AddCartItemRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException {
        Long productId = addToCartRequest.getProductId();
        Long buyerId = addToCartRequest.getBuyerId();
        Product gottenProduct = productService.getProductBy(productId);
        recordReserveInInventory(gottenProduct);
        CartItem foundCartItem = createCartItemIfItDoesNotExist(productId, gottenProduct, buyerId);
        CartItem savedCart = cartItemRepository.save(foundCartItem);
        return new ApiResponse<>("SUCCESSFUL");
    }

    private void recordReserveInInventory(Product gottenProduct) throws ProductInventoryNotFoundException {
        ProductInventory productInventory = gottenProduct.getProductInventory();
        Long productInventoryId = productInventory.getId();
        inventoryService.reserveProductBy(productInventoryId);
    }

    private CartItem createCartItemIfItDoesNotExist(Long productId, Product gottenProduct, Long buyerId) {
        Cart foundCart = cartService.findByBuyerId(buyerId);
        Long cartId = foundCart.getId();
        CartItem foundCartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId);
        if(foundCartItem == null){
            foundCartItem = createNewCartItem(gottenProduct, foundCart);
        }else foundCartItem.increaseItemQuantity();
        return foundCartItem;
    }

    private static CartItem createNewCartItem(Product gottenProduct, Cart foundCart) {
        CartItem foundCartItem;
        foundCartItem = new CartItem();
        foundCartItem.increaseItemQuantity();
        foundCartItem.setProduct(gottenProduct);
        foundCartItem.setCart(foundCart);
        return foundCartItem;
    }

    @Override
    @Transactional
    public ApiResponse<?> removeCartItem(AddCartItemRequest addToCartRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException, 
            CannotTakeOutCartItemThatDoesNotExistInYOurCartException {
        Long productId = addToCartRequest.getProductId();
        Long buyerId = addToCartRequest.getBuyerId();
        CartItem foundCartItem = checkIfCartItemExist(buyerId, productId);
        recordReturnInInventory(productId);
        foundCartItem.decreaseItemQuantity();
        CartItem savedCart = cartItemRepository.save(foundCartItem);
        return new ApiResponse<>("SUCCESSFUL");
    }

    private CartItem checkIfCartItemExist(Long buyerId, Long productId) throws 
            CannotTakeOutCartItemThatDoesNotExistInYOurCartException {
        Cart foundCart = cartService.findByBuyerId(buyerId);
        Long cartId = foundCart.getId();
        CartItem foundCartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId);
        if(foundCartItem == null) throw new CannotTakeOutCartItemThatDoesNotExistInYOurCartException(
                "You cannot reduce item that is not in your cart"
        );
        return foundCartItem;
    }

    private void recordReturnInInventory(Long productId) throws ProductNotFoundException, ProductInventoryNotFoundException {
        Product gottenProduct = productService.getProductBy(productId);
        ProductInventory productInventory = gottenProduct.getProductInventory();
        Long productInventoryId = productInventory.getId();
        ApiResponse<?> reservationResponse = inventoryService.returnReserveProductBy(productInventoryId);
    }



    @Override
    public List<CartItem> findAllCartItemBuyCartId(Long cartId) {
         return cartItemRepository.findAllByCartId(cartId);
    }

    @Override
    public List<CartItemDetails> getAllCartItems(Long cartId) {
        List<CartItem> cartItemList = findAllCartItemBuyCartId(cartId);
        return cartItemList.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDetails.class)).toList();
    }

    @Override
    public void clearCartItemsFromCart(List<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }
}
