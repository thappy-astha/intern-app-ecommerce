package com.intern.app.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;



@Entity   //a Java class that maps to a database table
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot exceed 100")
    private Long discount;

    @Positive(message = "Original price must be greater than 0")
    private long originalPrice;

    @PositiveOrZero(message = "Discount price cannot be negative")
    private long discountPrice;

    @Positive(message = "Size must be greater than 0")
    private Long size;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @JsonIgnore
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @NotNull(message = "Product image is required")
    private byte[] image;


    public Product() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public long getOriginalPrice() {
        return originalPrice;
    }
    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }


    public long getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getDiscount() {
        return discount;
    }
    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public byte[] getImage() {return image;}
    public void setImage(byte[] image) {this.image = image;}

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", discount=" + discount +
                ", originalPrice=" + originalPrice +
                ", discountPrice=" + discountPrice +
                ", size=" + size +
                ", description='" + description + '\'' +
                '}';
    }
}

