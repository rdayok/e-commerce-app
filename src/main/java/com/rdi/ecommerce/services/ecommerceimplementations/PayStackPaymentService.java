package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.config.PayStackConfig;
import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PaymentResponse;
import com.rdi.ecommerce.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class PayStackPaymentService implements PaymentService {

    private final PayStackConfig payStackConfig;
    @Override
    public PaymentResponse pay(PaymentRequest paymentRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String URL = payStackConfig.getInitialisePaymentUrl();
        httpHeaders.set("Authorization", payStackConfig.getAuthorization());
        httpHeaders.set("Content-Type", APPLICATION_JSON_VALUE);
        httpHeaders.set("accept", APPLICATION_JSON_VALUE);
        HttpEntity<PaymentRequest> requestHttpEntity = new RequestEntity<>(paymentRequest, httpHeaders, POST, URI.create(""));
        ResponseEntity<PaymentResponse> responseEntity = restTemplate.postForEntity(URL, requestHttpEntity, PaymentResponse.class);
        return responseEntity.getBody();
    }

}
