package com.onlineshop.productcatalog;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@DataJpaTest
public class ProductStorageTest {

    private Product product;

    @Autowired
    private ProductStorage productStorage;

    @BeforeEach
    public void beforeEach() {
        productStorage.deleteAll();
        product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Item1")
                .description("Description1")
                .photo("SomeURL1")
                .price(BigDecimal.valueOf(20))
                .stock(20)
                .build();
    }

    @Test
    public void saveProductTest() {
        productStorage.save(product);
        Assertions.assertThat(product.getId()).isNotNull();
    }

    @Test
    public void getProductTest() {
        Assertions.assertThat(product.getId()).isEqualTo(product.getId());
    }

    @Test
    public void getListOfProductsTest() {
        var product2 = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("product2")
                .description("desc2")
                .photo("url2")
                .price(BigDecimal.valueOf(20))
                .stock(200)
                .build();

        var product3 = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("product3")
                .description("desc3")
                .photo("url3")
                .price(BigDecimal.valueOf(30))
                .stock(300)
                .build();

        productStorage.saveAll(List.of(product, product2, product3));

        List<Product> products = productStorage.findAll();

        Assertions.assertThat(products.size()).isEqualTo(3);
    }

    @Test
    public void updateProductTest() {
        product.setName("updatedName");
        product.setDescription("updatedDescription");
        product.setPhoto("updatedPhotoURL");
        product.setStock(100);
        product.setPrice(BigDecimal.valueOf(50));

        Assertions.assertThat(product.getName()).isEqualTo("updatedName");
        Assertions.assertThat(product.getDescription()).isEqualTo("updatedDescription");
        Assertions.assertThat(product.getPhoto()).isEqualTo("updatedPhotoURL");
        Assertions.assertThat(product.getStock()).isEqualTo(100);
        Assertions.assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    public void deleteProductTest() {
        productStorage.delete(product);
        Assertions.assertThat(productStorage.count()).isEqualTo(0);
    }
}
