package catimageshop.basket;

import catimageshop.productcatalog.Product;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    public boolean isEmpty(Basket basket) {
        return basket.getProducts().isEmpty();
    }

    public void add(Product product, Basket basket) {
        if (product.getStock() <= 0) throw new NotEnoughQuantityException();
        if (!isInBasket(product, basket)) putIntoBasket(product, basket);
        else increaseProductQuantity(product, basket);
    }

    public void putIntoBasket(Product product, Basket basket) {
        basket.getProducts().put(product.getId(), product);
        basket.getProductsQuantities().put(product.getId(), 1);
    }

    public void increaseProductQuantity(Product product, Basket basket) {
        basket.getProductsQuantities().put(product.getId(), basket.getProductsQuantities().get(product.getId()) + 1);
    }

    public boolean isInBasket(Product product, Basket basket) {
        BasketService basketService = new BasketService();
        return basket.getProductsQuantities().containsKey(product.getId());
    }

    public void delete(Product product, Basket basket) {
        basket.getProducts().remove(product.getId(), product);
    }

    public Integer getDifferentProductsQuantities(Basket basket) {
        return basket.getProducts().size();
    }

    public Integer getCertainProductQuantity(Product product, Basket basket) {
        return basket.getProductsQuantities().get(product.getId());
    }
}