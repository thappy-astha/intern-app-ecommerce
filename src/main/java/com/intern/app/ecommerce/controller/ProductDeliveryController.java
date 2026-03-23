package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.dto.UpdateDeliveryStatusRequest;
import com.intern.app.ecommerce.dto.VendorOrderTrackerResponse;
import com.intern.app.ecommerce.model.ProductDelivery;
import com.intern.app.ecommerce.service.ProductDeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class ProductDeliveryController {

    private final ProductDeliveryService deliveryService;

    public ProductDeliveryController(ProductDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/create")
    public ProductDelivery create(@RequestParam Long productId,
                                  @RequestParam Long vendorId,
                                  @RequestParam Long userId,
                                  @RequestParam Integer quantity,
                                  @RequestParam Long orderId) {

        return deliveryService.createDelivery(productId, vendorId, userId, quantity, orderId);
    }

    @PutMapping("/status")
    public ProductDelivery updateStatus(@RequestBody UpdateDeliveryStatusRequest request) {
        return deliveryService.updateStatus(
                request.getDeliveryId(),
                request.getStatus()
        );
    }

    @GetMapping("/product/{productId}")
    public List<ProductDelivery> getByProduct(@PathVariable Long productId) {
        return deliveryService.getByProduct(productId);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<VendorOrderTrackerResponse> getByVendor(@PathVariable Long vendorId) {
        return deliveryService.getByVendor(vendorId);
    }

    @GetMapping("/user/{userId}")
    public List<ProductDelivery> getByUser(@PathVariable Long userId) {
        return deliveryService.getByUser(userId);
    }
}