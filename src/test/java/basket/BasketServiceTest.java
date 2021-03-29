package basket;

import catimageshop.basket.Basket;
import catimageshop.basket.BasketService;
import catimageshop.basket.NotEnoughQuantityException;
import catimageshop.productcatalog.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BasketServiceTest {

    public static final String PRODUCT_1_NAME = "TEST_NAME_PRODUCT_1";
    public static final Long PRODUCT_1_ID = 1L;
    public static final Integer PRODUCT_1_STOCK = 2;
    private static final Integer ZERO_STOCK_PRODUCT = 0;

    private BasketService basketService;

    @Before
    public void initEach() {
        basketService = new BasketService();
    }

    @Test
    public void createEmptyBasketTest() {
        Basket basket = new Basket();
        assertThat(basketService.isEmpty(basket)).isTrue();
    }

    @Test
    public void addProductToTheBasketTest() {
        //Arrange
        Basket basket = new Basket();
        Product product = createProduct();
        //Act
        basketService.add(product, basket);
        //Assert
        assertThat(basketService.isEmpty(basket)).isFalse();
    }

    @Test
    public void addProduct_ThatIsOutOfStock_AndThrowExceptionTest() {
        //Arrange
        Basket basket = new Basket();
        Product productWithZeroStock = createProductWithZeroStock();
        //Act
        Exception exception = assertThrows(NotEnoughQuantityException.class,
                () -> basketService.add(productWithZeroStock, basket));
        //Assert
        assertTrue((exception.getMessage()).contains("Not enough products in stock"));
    }

    @Test
    public void deleteProductTest() {
        //Arrange
        Basket basket = new Basket();
        Product product = createProduct();
        basketService.add(product, basket);
        //Act
        basketService.delete(product, basket);
        //Assert
        assertThat(basketService.isEmpty(basket)).isTrue();
    }

    @Test
    public void addSameProductTwice_AndCheckQuantityTest() {
        //Arrange
        Basket basket = new Basket();
        Product product1 = createProduct();
        //Act
        basketService.add(product1, basket);
        basketService.add(product1, basket);
        //Assert
        assertEquals(basketService.getDifferentProductsQuantities(basket), 1);
        assertEquals(basketService.getCertainProductQuantity(product1, basket), 2);
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
