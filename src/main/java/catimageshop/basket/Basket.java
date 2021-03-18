package catimageshop.basket;

import catimageshop.productcatalog.Product;

import java.util.HashMap;

public class Basket {
    private final HashMap<String, Product> products;
    private final HashMap<String, Integer> productQuantities;

    public Basket() {
        products = new HashMap<>();
        productQuantities = new HashMap<>();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public static Basket empty() {
        return new Basket();
    }

    public void add(Product product) {
        if(product.getStock()<=0) throw new NotEnoughQuantityException();
        if(!isInBasket(product)) products.put(product.getId(), product);
        else increaseProductQuantity(product);
    }

    private void increaseProductQuantity(Product product) {
        productQuantities.put(product.getId(), productQuantities.get(product.getId() +1));
    }

    private boolean isInBasket(Product product) {
        return productQuantities.containsKey(product.getId());
    }

    public void delete(Product product) {
        products.remove(product.getId(), product);
    }

    public Integer getDifferentProductsQuantities() {
        return products.size();
    }
}
