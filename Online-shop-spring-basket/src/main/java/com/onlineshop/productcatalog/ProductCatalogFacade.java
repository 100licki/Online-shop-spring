package com.onlineshop.productcatalog;

import com.onlineshop.productcatalog.exceptions.ProductNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductCatalogFacade {
    ProductStorage productStorage;

    public ProductCatalogFacade(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public String createProduct() {
        var product = new Product(UUID.randomUUID());
        productStorage.save(product);
        return product.getId();
    }

    public boolean checkIfExistsById(String productId) {
        return productStorage.findById(productId).isPresent();
    }

    public Product getById(String productId) {
        return getProductOrException(productId);
    }

    public void updateDetails(String productId, String productDesc, String productPicture) {
        var product = getProductOrException(productId);
        product.setDescription(productDesc);
        product.setPhoto(productPicture);
        productStorage.save(product);
    }

    public void applyPrice(String productId, BigDecimal valueOf) {
        var product = getProductOrException(productId);
        product.setPrice(valueOf);
        productStorage.save(product);
    }

    public List<Product> getAvailableProducts() {
        return productStorage.findAll();
    }

    private Product getProductOrException(String productId) {
        return productStorage.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format(
                        "There is no product with id: %s", productId)));
    }

    @Transactional
    public void emptyCatalog() {
        productStorage.deleteAll();
    }
}
