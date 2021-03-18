package basket;

import catimageshop.basket.Basket;
import catimageshop.productcatalog.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class basketTest {

    public static final String PRODUCT_1 = "TEST_NAME_PRODUCT_1";
    private static final String PRODUCT_2 = "TEST_NAME_PRODUCT_2";

    @Test
    public void createEmptyBasketTest() {
        Basket basket = Basket.empty();
        assertThat(basket.isEmpty()).isTrue();
    }

    @Test
    public void addProductToTheBasketTest() {
        //Arrange
        Basket basket = new Basket();
        Product product = thereIsProduct(PRODUCT_1);
        //Act
        basket.add(product);
        //Assert
        assertThat(basket.isEmpty()).isFalse();
    }

    private Product thereIsProduct(String productName) {
        Product product = new Product();
        product.setName(productName);
        return product;
    }

    @Test
    public void deleteProductTest() {
        //Arrange
        Basket basket = new Basket();
        Product product = thereIsProduct(PRODUCT_1);
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
        Product product1 = thereIsProduct(PRODUCT_1);
        //Act
        basket.add(product1);
        basket.add(product1);
        //Assert
        assertThat(basket.getDifferentProductsQuantities()).isEqualTo(1);
        assertThat(basket.getCertainProductQuantity()).isEqualTo(2);
    }
}
