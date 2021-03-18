package catimageshop.productcatalog;

import java.util.Map;

public class HashMapProductStorage {
    Map<String, Product> products;

    public HashMapProductStorage(Map<String, Product> products) {
        this.products = products;
    }
}
