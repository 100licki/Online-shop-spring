package com.onlineshop.productcatalog.exceptions;

public class ProductNotFoundException extends IllegalStateException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
