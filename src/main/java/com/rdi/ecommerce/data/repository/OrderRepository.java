package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.BuyerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<BuyerOrder, Long> {
}
