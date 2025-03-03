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

    public Product saveProductServie(Product product) {
        Product updateProduct = productRepository.findById(product.getId()).orElse(null);
        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setOrigin(product.getOrigin());
        updateProduct.setDetailDesc(product.getDetailDesc());
        updateProduct.setShortDesc(product.getShortDesc());
        updateProduct.setSold(product.getSold());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setImage(product.getImage());
        return this.productRepository.save(updateProduct);
    }
}
