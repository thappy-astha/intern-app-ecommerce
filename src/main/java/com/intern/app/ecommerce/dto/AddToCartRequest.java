package com.intern.app.ecommerce.dto;

public class AddToCartRequest {

    private Long userId;
    private Long productId;
    private Integer quantity;

    public Long getUserId() { return userId; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}