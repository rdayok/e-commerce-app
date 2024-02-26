package com.rdi.ecommerce.data.model;

import com.rdi.ecommerce.dto.PayStackPaymentResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class PaymentReceipt {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String paymentStatus;
    private Boolean authorizationStatus;
    private String message;
    private String authorization_url;
    private String access_code;
    private String reference;

}
