package com.onlineshop.productcatalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class ProductCatalogConfiguration {

    @Bean
    public ProductCatalogFacade productCatalogFacade(ProductStorage productStorage) {
        return new ProductCatalogFacade(productStorage);
    }

    @Bean
    CommandLineRunner commandLineRunner2(ProductStorage productStorage) {
        return args -> {

            var item = new Product();
            item.setName("spoon");
            item.setDescription("small spoon");
            item.setPhoto("spoonURL");
            item.setPrice(BigDecimal.valueOf(5));
            item.setStock(10);

            var item2 = new Product();
            item2.setName("knife");
            item2.setDescription("average knife");
            item2.setPhoto("knifeURL");
            item2.setPrice(BigDecimal.valueOf(10));
            item2.setStock(20);

            productStorage.saveAll(List.of(item, item2));
        };
    }
}
