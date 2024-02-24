package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PaymentResponse;
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
    public void testPayment() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail("dayokr@gmail.com");
        paymentRequest.setAmount(BigDecimal.valueOf(1000));

        PaymentResponse paymentResponse = paymentService.pay(paymentRequest);

        assertThat(paymentResponse).isNotNull();
        log.info("{}", paymentResponse);
    }
}
