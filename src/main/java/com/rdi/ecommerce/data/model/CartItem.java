package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
public class CartItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private int itemQuantity;
    @OneToOne(cascade = DETACH)
    private Product product;
    @ManyToOne(cascade = DETACH)
    private Cart cart;
}
