package com.rdi.ecommerce.data.model;

import com.rdi.ecommerce.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String productName;
    private String productDescription;
    @ManyToOne
    private Store store;
    @Enumerated(EnumType.STRING)
    private Category productCategory;
    @OneToOne(cascade = CascadeType.ALL)
    private ProductInventory productInventory;
    private BigDecimal pricePerUnit;
    private LocalDateTime dateProductWasAdded;

    @PrePersist
    private void setDateProductWasAdded() {
        dateProductWasAdded = LocalDateTime.now();
    }
}
