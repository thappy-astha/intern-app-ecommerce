package com.intern.app.ecommerce.dto;

import com.intern.app.ecommerce.model.DeliveryStatus;

public class UpdateDeliveryStatusRequest {

    private Long deliveryId;
    private DeliveryStatus status;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}