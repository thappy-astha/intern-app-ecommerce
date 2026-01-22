package com.intern.app.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage() {}

    public ProductImage(byte[] image, Product product) {
        this.image = image;
        this.product = product;
    }

    public long getId() { return id; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
