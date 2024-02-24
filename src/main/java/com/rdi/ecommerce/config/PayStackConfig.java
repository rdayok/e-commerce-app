package com.rdi.ecommerce.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PayStackConfig {

    @Value("${pay_stack_initialise_payment_url}")
    private String initialisePaymentUrl;
    @Value("${pay_stack_verify_payment_url}")
    private String verifyPaymentUrl;
    @Value("${authorization}")
    private String authorization;
}
