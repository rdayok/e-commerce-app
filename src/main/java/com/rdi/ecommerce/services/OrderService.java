package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.PaymentResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;

public interface OrderService {
    PaymentResponse initialisePayment(Long buyerId) throws BuyerNotFoundException;
}
