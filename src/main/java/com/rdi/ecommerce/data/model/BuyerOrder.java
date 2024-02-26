package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class BuyerOrder {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToMany(cascade = ALL)
    private List<OrderItem> orderItemsList;
    @OneToOne(cascade = ALL)
    private PaymentReceipt paymentReceipt;
    @OneToOne(cascade = DETACH)
    private Buyer buyer;
}
