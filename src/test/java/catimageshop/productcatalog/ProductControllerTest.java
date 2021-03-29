package catimageshop.productcatalog;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Ignore
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String hostName;
    private String uri;

    @BeforeEach
    public void before() throws UnknownHostException {
        productRepository.deleteAll();
        hostName = InetAddress.getLocalHost().getHostAddress();
        StringBuilder stringBuilder = new StringBuilder();
        uri = stringBuilder
                .append("http://")
                .append(hostName)
                .append(":")
                .append(port).toString();
    }

    @Test
    void testGetProduct() throws URISyntaxException {
        Product product = new Product();
        product.setName("TEST NAME");
        product.setDescription("TEST DESC");
        product.setPhoto(null);

        HttpEntity<Product> request = new HttpEntity<>(product, null);

        final String baseUrl = uri+"/products";
        URI uri = new URI(baseUrl);
        this.restTemplate.postForEntity(uri, request, String.class);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
        assertNotNull(result);
    }

    @Test
    void testAddProduct() throws URISyntaxException {
        final String baseUrl = uri+"/products";
        URI uri = new URI(baseUrl);

        Product product = new Product();
        product.setName("TEST NAME");
        product.setDescription("TEST DESC");
        product.setPhoto(null);

        HttpEntity<Product> request = new HttpEntity<>(product, null);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        assertNotNull(result);
    }

    @Test
    void testUpdateProduct() throws URISyntaxException {
        final String baseUrl = uri+"/products";
        URI uri = new URI(baseUrl);

        Product product = new Product();
        product.setName("TEST NAME");
        product.setDescription("TEST DESC");
        product.setPhoto(null);

        HttpEntity<Product> request = new HttpEntity<>(product, null);

        this.restTemplate.postForEntity(uri, request, String.class);
        ResponseEntity<Product[]> result = this.restTemplate.getForEntity(uri, Product[].class);
        assertNotNull(result);
        Product productFromDb = Objects.requireNonNull(result.getBody())[0];
        assertNotNull(productFromDb);

        productFromDb.setName("CHANGED NAME");
        productFromDb.setDescription("CHANGED DESC");

        HttpEntity<Product> request2 = new HttpEntity<>(productFromDb, null);

        this.restTemplate.put(uri, request2);
        ResponseEntity<Product[]> result2 = this.restTemplate.getForEntity(uri, Product[].class);
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
        final String baseUrl = uri+"/products";
        URI uri = new URI(baseUrl);

        Product product = new Product();
        product.setName("TEST NAME");
        product.setDescription("TEST DESC");
        product.setPhoto(null);

        HttpEntity<Product> request = new HttpEntity<>(product, null);

        this.restTemplate.postForEntity(uri, request, String.class);
        ResponseEntity<Product[]> result = this.restTemplate.getForEntity(uri, Product[].class);
        assertNotNull(result);
        Product productFromDb = Objects.requireNonNull(result.getBody())[0];
        assertNotNull(productFromDb);


        final String baseUrl2 = uri+"/products/" + productFromDb.getId();
        URI uri2 = new URI(baseUrl2);

        this.restTemplate.delete(String.valueOf(uri2));

        ResponseEntity<Product[]> emptyArrayOfProducts = this.restTemplate.getForEntity(uri, Product[].class);
        assertThat(emptyArrayOfProducts.getBody()).hasSize(0);
    }
}