package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

//    Optional<Store> findByMerchantId(Long merchantId);
}
