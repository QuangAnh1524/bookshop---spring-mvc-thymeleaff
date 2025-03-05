package com.quanh1524.bookshop.repository;

import com.quanh1524.bookshop.domain.Cart;
import com.quanh1524.bookshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);
}
