package com.onlineshop.productcatalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStorage extends JpaRepository<Product, String> {
}
