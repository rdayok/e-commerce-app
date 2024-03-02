package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CheckOutResponse;
import com.rdi.ecommerce.dto.VerifyingCheckoutPaymentRequest;
import com.rdi.ecommerce.exceptions.AddressNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerOrderNotFoundException;

public interface BuyerOrderService {
    CheckOutResponse checkOut(Long buyerId) throws BuyerNotFoundException, AddressNotFoundException;

    ApiResponse<?> verifyCheckOutPayment(VerifyingCheckoutPaymentRequest verifyingCheckoutPaymentRequest) throws
            BuyerOrderNotFoundException, BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException;
}
