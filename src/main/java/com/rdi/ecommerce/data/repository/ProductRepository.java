package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
