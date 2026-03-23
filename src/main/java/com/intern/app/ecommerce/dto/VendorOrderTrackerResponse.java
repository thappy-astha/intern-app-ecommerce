package com.intern.app.ecommerce.dto;

import com.intern.app.ecommerce.model.DeliveryStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendorOrderTrackerResponse {

    private Long orderId;
    private Long id;
    private String productName;
    private Integer deliveredQuantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime updatedAt;

    public VendorOrderTrackerResponse(Long orderId,
                                      Long id,
                                      String productName,
                                      Integer deliveredQuantity,
                                      BigDecimal unitPrice,
                                      BigDecimal totalPrice,
                                      DeliveryStatus status,
                                      LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.id = id;
        this.productName = productName;
        this.deliveredQuantity = deliveredQuantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.status = status != null ? status.name() : null;
        this.updatedAt = updatedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}