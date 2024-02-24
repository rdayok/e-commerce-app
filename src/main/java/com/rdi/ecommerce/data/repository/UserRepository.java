package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
