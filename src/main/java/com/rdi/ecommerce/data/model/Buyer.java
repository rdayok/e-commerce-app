package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Setter
@Getter
@ToString
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;
    private String phoneNumber;
    @OneToOne(cascade = ALL)
    private Cart cart;
}
