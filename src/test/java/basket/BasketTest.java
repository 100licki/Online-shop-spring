package basket;

import catimageshop.basket.Basket;
import catimageshop.basket.NotEnoughQuantityException;
import catimageshop.productcatalog.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BasketTest {

    public static final String PRODUCT_1_NAME = "TEST_NAME_PRODUCT_1";
    public static final String PRODUCT_1_ID = "1";
    public static final Integer PRODUCT_1_STOCK = 2;
    private static final Integer ZERO_STOCK_PRODUCT = 0;

    @Test
    public void createEmptyBasketTest() {
        Basket basket = Basket.empty();
        assertThat(basket.isEmpty()).isTrue();
    }

    @Test
    public void addProductToTheBasketTest() {
        //Arrange
        Basket basket = new Basket();
        Product product = createProduct();
        //Act
        basket.add(product);
        //Assert
        assertThat(basket.isEmpty()).isFalse();
    }

    @Test
    public void addProduct_ThatIsOutOfStock_AndThrowExceptionTest() {
        //Arrange
        Basket basket = new Basket();
        Product productWithZeroStock = createProductWithZeroStock();
        //Act
        Exception exception = assertThrows(NotEnoughQuantityException.class,
                () -> basket.add(productWithZeroStock));
        //Assert
        assertTrue((exception.getMessage()).contains("Not enough products in stock"));
    }

    @Test
    public void deleteProductTest() {
        //Arrange
        Basket basket = new Basket();
        Product product = createProduct();
        basket.add(product);
        //Act
        basket.delete(product);
        //Assert
        assertThat(basket.isEmpty()).isTrue();
    }

    @Test
    public void addSameProductTwice_AndCheckQuantityTest() {
        //Arrange
        Basket basket = new Basket();
        Product product1 = createProduct();
        //Act
        basket.add(product1);
        basket.add(product1);
        //Assert
        assertEquals(basket.getDifferentProductsQuantities(), 1);
        assertEquals(basket.getCertainProductQuantity(product1), 2);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(PRODUCT_1_ID);
        product.setName(PRODUCT_1_NAME);
        product.setStock(PRODUCT_1_STOCK);
        return product;
    }

    private Product createProductWithZeroStock() {
        Product product = new Product();
        product.setId(PRODUCT_1_ID);
        product.setName(PRODUCT_1_NAME);
        product.setStock(ZERO_STOCK_PRODUCT);
        return product;
    }
}
