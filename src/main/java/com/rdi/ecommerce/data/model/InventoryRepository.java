package com.rdi.ecommerce.data.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<ProductRecord, Long> {
}
