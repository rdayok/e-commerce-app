package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne(cascade = DETACH)
    private Buyer buyer;
//    @OneToMany(fetch = FetchType.EAGER, cascade = DETACH)
//    private List<CartItem> cartItems;
}
