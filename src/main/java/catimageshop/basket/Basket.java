package catimageshop.basket;

import catimageshop.productcatalog.Product;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private final Map<String, Product> products;
    private final Map<String, Integer> productsQuantities;

    public Basket() {
        products = new HashMap<>();
        productsQuantities = new HashMap<>();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public static Basket empty() {
        return new Basket();
    }

    public void add(Product product) {
        if (product.getStock() <= 0) throw new NotEnoughQuantityException();
        if (!isInBasket(product)) putIntoBasket(product);
        else increaseProductQuantity(product);
    }

    private void putIntoBasket(Product product) {
        products.put(product.getId(), product);
        productsQuantities.put(product.getId(), 1);
    }

    private void increaseProductQuantity(Product product) {
        productsQuantities.put(product.getId(), productsQuantities.get(product.getId()) + 1);
    }

    private boolean isInBasket(Product product) {
        return productsQuantities.containsKey(product.getId());
    }

    public void delete(Product product) {
        products.remove(product.getId(), product);
    }

    public Integer getDifferentProductsQuantities() {
        return products.size();
    }

    public Integer getCertainProductQuantity(Product product) {
        return this.productsQuantities.get(product.getId());
    }
}
