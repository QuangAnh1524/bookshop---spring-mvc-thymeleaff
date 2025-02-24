package com.quanh1524.bookshop.repository;

import com.quanh1524.bookshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductById(long id);

    Product deleteById(long id);

}
