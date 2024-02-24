package com.rdi.ecommerce.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "inventories")
@Setter
@Getter
public class Inventory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
