package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
