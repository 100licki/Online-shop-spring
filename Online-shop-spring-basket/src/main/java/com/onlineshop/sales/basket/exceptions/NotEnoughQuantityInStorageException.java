package com.onlineshop.sales.basket.exceptions;

import com.onlineshop.productcatalog.Product;

public class NotEnoughQuantityInStorageException extends IllegalStateException {
    public NotEnoughQuantityInStorageException() {
        super("Not enough products in stock");
    }

    public NotEnoughQuantityInStorageException(Product product) {
        super(String.format("Not enough %s products in stock. Only %d left", product.getName(), product.getStock()));
    }
}
