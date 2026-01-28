package com.intern.app.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage() {}

    public ProductImage(String imageUrl, Product product) {
        this.imageUrl = imageUrl;
        this.product = product;
    }

    public long getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String image) { this.imageUrl = image; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
