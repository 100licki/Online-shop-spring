package com.onlineshop.productcatalog;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductStorage productRepository;

    public ProductService(ProductStorage productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.findAll());
    }

    public Optional<Product> getProduct(String id) {
        return productRepository.findById(id);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
