package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
