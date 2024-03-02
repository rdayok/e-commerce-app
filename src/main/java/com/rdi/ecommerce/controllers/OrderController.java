package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.data.model.BuyerOrder;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CheckOutResponse;
import com.rdi.ecommerce.dto.VerifyingCheckoutPaymentRequest;
import com.rdi.ecommerce.exceptions.AddressNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerOrderNotFoundException;
import com.rdi.ecommerce.services.BuyerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final BuyerOrderService buyerOrderService;

    @PostMapping("checkout/{buyerId}")
    public ResponseEntity<CheckOutResponse> checkOut(@PathVariable Long buyerId) throws
            BuyerNotFoundException, AddressNotFoundException {
        return ResponseEntity.status(CREATED).body(buyerOrderService.checkOut(buyerId));
    }

    @PostMapping("/verify_payment")
    public ResponseEntity<ApiResponse<?>> verifyCheckoutPayment(
            @RequestBody VerifyingCheckoutPaymentRequest verifyingCheckoutPaymentRequest
    ) throws BuyerOrderNotFoundException, BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException {
        return ResponseEntity.status(OK).body(buyerOrderService.verifyCheckOutPayment(verifyingCheckoutPaymentRequest));
    }
}
