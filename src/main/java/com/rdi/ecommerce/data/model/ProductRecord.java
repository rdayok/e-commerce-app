package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class ProductRecord {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private int availableQuantity;
    private int reservedQuantity;
    private Long soldQuantity;
    @ManyToOne(cascade = DETACH)
    private Store store;
    @OneToOne(cascade = DETACH)
    private Product product;
}
