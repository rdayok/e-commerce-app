package com.rdi.ecommerce.data.repository;

import com.rdi.ecommerce.data.model.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT b FROM CartItem b WHERE b.product.id = :productId AND b.cart.id = :cartId")
    CartItem findByProductIdAndCartId(@Param("productId") Long productId, @Param("cartId") Long cartId);

}
