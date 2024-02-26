package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PaymentResponse;
import com.rdi.ecommerce.dto.VerifyPaymentResponse;

public interface PaymentService {

    PaymentResponse initialisePayment(PaymentRequest paymentRequest);

    VerifyPaymentResponse verifyPayment(String paymentReference);
}
