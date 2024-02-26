package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.CheckOutResponse;
import com.rdi.ecommerce.dto.PayStackPaymentResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;

public interface OrderService {
    CheckOutResponse checkOut(Long buyerId) throws BuyerNotFoundException;
}
