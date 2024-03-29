package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String username);

    User findUserByRole(Role role);
}
