package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.AddCartItemRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CartItemDetails;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.CannotTakeOutCartItemThatDoesNotExistInYOurCartException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/cart_item")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addCartItem(@RequestBody AddCartItemRequest addCartItemRequest) throws
            BuyerNotFoundException, ProductNotFoundException, ProductInventoryNotFoundException {
        return ResponseEntity.status(CREATED).body(cartItemService.addCartItem(addCartItemRequest));
    }

    @PostMapping("/remove")
    public ResponseEntity<ApiResponse<?>> removeCartItem(@RequestBody AddCartItemRequest addCartItemRequest) throws
            ProductNotFoundException, ProductInventoryNotFoundException,
            CannotTakeOutCartItemThatDoesNotExistInYOurCartException {
        return ResponseEntity.status(CREATED).body(cartItemService.removeCartItem(addCartItemRequest));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItemDetails>> getAllCartItems(@PathVariable Long cartId) {
        return ResponseEntity.status(OK).body(cartItemService.getAllCartItems(cartId));
    }

}
