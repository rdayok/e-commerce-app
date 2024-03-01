package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
   Optional<Address> findByUserId(Long userId);
}
