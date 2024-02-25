package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByBuyerId(Long buyerId);
}
