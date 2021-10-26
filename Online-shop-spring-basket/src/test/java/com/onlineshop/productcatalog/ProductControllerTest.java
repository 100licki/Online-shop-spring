package com.onlineshop.productcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private static final String URL_PREFIX = "http://localhost:";
    private static final String URL_SUFFIX = "/products";

    private URI uri;

    @LocalServerPort
    private int port;

    @Autowired
    private ProductStorage productStorage;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void before() throws URISyntaxException {
        productStorage.deleteAll();
        uri = getUri();
    }

    @Test
    void testAddProduct() {
        var product = createProduct();
        var request = new HttpEntity<>(product, null);
        var result = this.restTemplate.postForEntity(uri, request, String.class);

        assertNotNull(result);
    }

    @Test
    void testGetProduct() {
        var product = createProduct();
        var request = new HttpEntity<>(product, null);
        postEntity(request);
        var result = this.restTemplate.getForEntity(uri, String.class);

        assertNotNull(result);
    }

    @Test
    void testUpdateProduct() {
        var product = createProduct();
        var request = new HttpEntity<>(product, null);
        postEntity(request);
        var result = this.restTemplate.getForEntity(uri, Product[].class);

        assertNotNull(result);

        var productFromDb = Objects.requireNonNull(result.getBody())[0];

        assertNotNull(productFromDb);

        productFromDb.setName("CHANGED NAME");
        productFromDb.setDescription("CHANGED DESC");
        var request2 = new HttpEntity<>(productFromDb, null);
        this.restTemplate.put(uri, request2);
        var result2 = this.restTemplate.getForEntity(uri, Product[].class);

        assertThat(result2.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(result2.getBody())
                .hasSize(1)
                .extracting(Product::getDescription)
                .contains("CHANGED DESC")
                .doesNotContain("TEST DESC");
    }

    @Test
    void testDeleteProduct() throws URISyntaxException {
        var product = createProduct();
        var request = new HttpEntity<>(product, null);
        postEntity(request);
        var result = this.restTemplate.getForEntity(uri, Product[].class);

        assertNotNull(result);

        var productFromDb = Objects.requireNonNull(result.getBody())[0];

        assertNotNull(productFromDb);

        final var urlWithId = "http://localhost:" + port + "/products/" + productFromDb.getId();
        var uriWithId = new URI(urlWithId);
        this.restTemplate.delete(String.valueOf(uriWithId));
        var emptyArrayOfProducts = this.restTemplate.getForEntity(uri, Product[].class);

        assertThat(emptyArrayOfProducts.getBody()).hasSize(0);
    }

    @Test
    public void testListAllAvailableProducts() {
        var product = createProduct();
        var product2 = createProduct2();
        var request = new HttpEntity<>(product, null);
        postEntity(request);
        request = new HttpEntity<>(product2, null);
        postEntity(request);
        var result = this.restTemplate.getForEntity(uri, Product[].class);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .hasSize(2)
                .extracting(Product::getDescription)
                .contains("TEST DESC", "TEST DESC2");
    }

    private Product createProduct() {
        var product = new Product();
        product.setName("TEST NAME");
        product.setDescription("TEST DESC");
        product.setPhoto(null);
        return product;
    }

    private Product createProduct2() {
        var product2 = new Product();
        product2.setName("TEST NAME2");
        product2.setDescription("TEST DESC2");
        product2.setPhoto(null);
        return product2;
    }

    private void postEntity(HttpEntity<Product> request) {
        this.restTemplate.postForEntity(uri, request, String.class);
    }

    private URI getUri() throws URISyntaxException {
        return new URI(URL_PREFIX + port + URL_SUFFIX);
    }
}