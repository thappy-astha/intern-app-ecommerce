package com.intern.app.ecommerce.model;

import jakarta.persistence.*;




@Entity   //a Java class that maps to a database table
@Table(name ="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int quantity;
    private long discount;
    private long originalPrice;
    private long discountPrice;
    private long size;
    private String description;


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


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

