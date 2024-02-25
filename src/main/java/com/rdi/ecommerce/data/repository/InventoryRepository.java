package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<ProductInventory, Long> {
}
