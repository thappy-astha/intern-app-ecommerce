package com.intern.app.ecommerce.dto;

public class UpdateCartRequest {

    private Long cartId;
    private Integer quantity;

    public Long getCartId() { return cartId; }
    public Integer getQuantity() { return quantity; }

    public void setCartId(Long cartId) { this.cartId = cartId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}