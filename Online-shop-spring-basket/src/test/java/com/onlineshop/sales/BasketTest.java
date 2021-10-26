package com.onlineshop.sales;

import com.onlineshop.productcatalog.Product;
import com.onlineshop.productcatalog.ProductStorage;
import com.onlineshop.sales.basket.Basket;
import com.onlineshop.sales.basket.exceptions.NotEnoughQuantityInStorageException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BasketTest {

    @Autowired
    private ProductStorage productStorage;

    private static final String PRODUCT_1_NAME = "TEST_NAME_PRODUCT_1";
    private static final String PRODUCT_1_ID = "1";
    private static final Integer PRODUCT_1_STOCK = 2;

    @Test
    public void createEmptyBasketTest() {
        var basket = Basket.createEmpty(productStorage);
        assertThat(basket.isEmpty()).isTrue();
    }

    @Test
    public void addProductToTheBasketTest() {
        var basket = getBasket();
        Product product = createProduct();
        basket.add(product);
        assertThat(basket.isEmpty()).isFalse();
    }

    @Test
    public void addProduct_ThatIsOutOfStock_AndThrowExceptionTest() {
        var basket = getBasket();
        var productWithZeroStock = createProductWithZeroStock();
        Exception exception = assertThrows(NotEnoughQuantityInStorageException.class,
                () -> basket.add(productWithZeroStock));
        assertTrue((exception.getMessage()).contains("Not enough products in stock"));
    }

    @Test
    public void deleteProductTest() {
        var basket = getBasket();
        var product = createProduct();
        basket.add(product);
        basket.delete(product);
        assertThat(basket.isEmpty()).isTrue();
    }

    @Test
    public void addSameProductTwice_AndCheckQuantityTest() {
        var basket = getBasket();
        var product = createProduct();
        basket.add(product);
        basket.add(product);
        assertEquals(basket.productsInBasket(), 1);
        assertEquals(basket.productQuantity(product), 2);
    }

    private Basket getBasket() {
        return new Basket(productStorage);
    }

    private Product createProduct() {
        var product = new Product();
        product.setId(PRODUCT_1_ID);
        product.setName(PRODUCT_1_NAME);
        product.setStock(PRODUCT_1_STOCK);
        return product;
    }

    private Product createProductWithZeroStock() {
        var product = new Product();
        product.setId(PRODUCT_1_ID);
        product.setName(PRODUCT_1_NAME);
        product.setStock(0);
        return product;
    }
}