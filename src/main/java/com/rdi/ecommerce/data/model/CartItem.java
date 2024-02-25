package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private int itemQuantity = 0;
    @OneToOne(cascade = DETACH)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cart")
    private Cart cart;

    public void increaseItemQuantity() {
        itemQuantity++;
    }
}
