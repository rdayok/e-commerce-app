package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.config.PayStackConfig;
import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PayStackPaymentResponse;
import com.rdi.ecommerce.dto.VerifyPaymentResponse;
import com.rdi.ecommerce.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class PayStackPaymentService implements PaymentService {

    private final PayStackConfig payStackConfig;
    @Override
    public PayStackPaymentResponse initialisePayment(PaymentRequest paymentRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String URL = payStackConfig.getInitialisePaymentUrl();
        HttpHeaders httpHeaders = getHttpHeadersForCloudinary();
        HttpEntity<PaymentRequest> requestHttpEntity =
                new RequestEntity<>(paymentRequest, httpHeaders, POST, URI.create(""));
        ResponseEntity<PayStackPaymentResponse> responseEntity =
                restTemplate.postForEntity(URL, requestHttpEntity, PayStackPaymentResponse.class);
        return responseEntity.getBody();
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String paymentReference) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpHeadersForCloudinary();
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        RequestEntity<String> requestEntity = new RequestEntity<>(httpHeaders, GET, URI.create(""));
        String rootURL = payStackConfig.getVerifyPaymentUrl();
        String URL = String.format("%s%s", rootURL, paymentReference);
        ResponseEntity<VerifyPaymentResponse> verifyPaymentResponseResponseEntity = restTemplate.exchange(URL, GET, httpEntity, VerifyPaymentResponse.class);
        return verifyPaymentResponseResponseEntity.getBody();
    }

    private HttpHeaders getHttpHeadersForCloudinary() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", payStackConfig.getAuthorization());
        httpHeaders.set("Content-Type", APPLICATION_JSON_VALUE);
        httpHeaders.set("accept", APPLICATION_JSON_VALUE);
        return httpHeaders;
    }

}
