package com.rdi.ecommerce.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Integer availableQuantity;
    private Integer reservedQuantity = 0;
    private Long soldQuantity;

    public void reserveOneProduct() {
        availableQuantity--;
        reservedQuantity++;
    }

    public void restockProduct(Integer quantityOfProductToRestock) {
        availableQuantity += quantityOfProductToRestock;
    }
}