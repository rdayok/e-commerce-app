package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse initialisePayment(PaymentRequest paymentRequest);
}
