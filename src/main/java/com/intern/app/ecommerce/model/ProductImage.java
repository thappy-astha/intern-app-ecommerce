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
    @Column(name = "image_data", columnDefinition = "LONGBLOB", nullable = false)
    @JsonIgnore
    private byte[] imageData;


    @Column(name = "content_type", length = 50)
    private String contentType;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage() {}

    public ProductImage(byte[] imageData, String contentType, Product product) {
        this.imageData = imageData;
        this.contentType = contentType;
        this.product = product;
    }

    public long getId() { return id; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    @Transient
    public String getImageUrl() {
        return "/api/product/image/" + this.id;
    }

}
