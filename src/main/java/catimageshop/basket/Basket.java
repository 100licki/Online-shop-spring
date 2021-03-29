package catimageshop.basket;

import catimageshop.productcatalog.Product;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Basket {
    private final Map<Long, Product> products;
    private final Map<Long, Integer> productsQuantities;

    public Basket() {
        products = new HashMap<>();
        productsQuantities = new HashMap<>();
    }
}