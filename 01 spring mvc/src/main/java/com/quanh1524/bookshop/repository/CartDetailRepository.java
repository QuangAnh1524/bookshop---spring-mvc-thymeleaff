package com.quanh1524.bookshop.repository;

import com.quanh1524.bookshop.domain.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartIdAndProductId(Long cartId, Long productId);
}
