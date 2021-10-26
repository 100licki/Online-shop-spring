package com.onlineshop.sales.basket;

import com.onlineshop.productcatalog.ProductStorage;
import com.onlineshop.sales.basket.exceptions.NotEnoughQuantityInStorageException;
import com.onlineshop.productcatalog.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class Basket {
    private final Map<Product, Integer> basket = new HashMap<>();
    private final ProductStorage productStorage;

    public Basket(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public boolean isEmpty() {
        return basket.isEmpty();
    }

    public static Basket createEmpty(ProductStorage productStorage) {
        return new Basket(productStorage);
    }

    public Map<Product, Integer> getBasket() {
        return Collections.unmodifiableMap(basket);
    }

    public void add(Product product) {
        if (product.getStock() <= 0) throw new NotEnoughQuantityInStorageException();
        if (!isInBasket(product)) putIntoBasket(product);
        else increaseProductQuantity(product);
    }

    public void delete(Product product) {
        basket.remove(product);
    }

    public void remove(Product product) {
        if (basket.containsKey(product)) {
            if (basket.get(product) > 1) {
                basket.replace(product, basket.get(product) - 1);
            } else if (basket.get(product) == 1) {
                basket.remove(product);
            }
        }
    }

    public Integer productsInBasket() {
        return basket.size();
    }

    public Integer productQuantity(Product product) {
        return this.basket.get(product);
    }

    public BigDecimal getTotal() {
        return basket.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void checkout() throws NotEnoughQuantityInStorageException {
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            Product product = productStorage.getOne(entry.getKey().getId());
            if (product.getStock() < entry.getValue())
                throw new NotEnoughQuantityInStorageException(product);
            entry.getKey().setStock(product.getStock() - entry.getValue());
        }
        productStorage.saveAll(basket.keySet());
        productStorage.flush();
        basket.clear();
    }

    private void putIntoBasket(Product product) {
        basket.put(product, 1);
    }

    private void increaseProductQuantity(Product product) {
        basket.put(product, basket.get(product) + 1);
    }

    private boolean isInBasket(Product product) {
        return basket.containsKey(product);
    }
}
