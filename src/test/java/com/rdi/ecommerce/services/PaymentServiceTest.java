package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PayStackPaymentResponse;
import com.rdi.ecommerce.dto.VerifyPaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testInitialisePayment() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail("dayokr@gmail.com");
        paymentRequest.setAmount(BigDecimal.valueOf(5000));

        PayStackPaymentResponse paymentResponse = paymentService.initialisePayment(paymentRequest);

        assertThat(paymentResponse).isNotNull();
        log.info("{}", paymentResponse);
    }


    @Test
    public void testVerifyPayment() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail("dayokr@gmail.com");
        paymentRequest.setAmount(BigDecimal.valueOf(5000));
        PayStackPaymentResponse paymentResponse = paymentService.initialisePayment(paymentRequest);
        assertThat(paymentResponse).isNotNull();
        String paymentReference = paymentResponse.getData().getReference();

        VerifyPaymentResponse verifyPaymentResponse = paymentService.verifyPayment(paymentReference);

        assertThat(verifyPaymentResponse).isNotNull();
        log.info("{}", verifyPaymentResponse);
    }


}
