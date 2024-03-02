package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.CartResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{buyerId}")
    public ResponseEntity<CartResponse> createCart(@PathVariable Long buyerId) throws BuyerNotFoundException {
        return ResponseEntity.status(CREATED).body(cartService.createCart(buyerId));
    }
}
