package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;


@Entity (name = "merchants")
@Setter
@Getter
@ToString
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = ALL)
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private Store store;
}
