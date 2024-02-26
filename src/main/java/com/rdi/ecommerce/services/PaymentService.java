package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PayStackPaymentResponse;
import com.rdi.ecommerce.dto.VerifyPaymentResponse;

public interface PaymentService {

    PayStackPaymentResponse initialisePayment(PaymentRequest paymentRequest);

    VerifyPaymentResponse verifyPayment(String paymentReference);

}
