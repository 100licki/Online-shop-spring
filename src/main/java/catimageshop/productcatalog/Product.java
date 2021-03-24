package catimageshop.productcatalog;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    public Product() {
    }

    public Product(String name, String description, String photo, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.stock = stock;
    }
}