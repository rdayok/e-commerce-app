package com.rdi.ecommerce.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne(cascade = DETACH)
    private Product product;
    private Integer itemQuantity;
    private BigDecimal costOfItem;

}
