package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.Product;
import com.quanh1524.bookshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.getProductById(id);
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
