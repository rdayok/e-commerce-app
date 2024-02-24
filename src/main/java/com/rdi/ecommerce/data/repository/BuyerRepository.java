package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
