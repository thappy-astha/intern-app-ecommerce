package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.dto.UpdateDeliveryStatusRequest;
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

    //create delivery
    @PostMapping("/create")
    public ProductDelivery create(@RequestParam Long productId,
                                  @RequestParam Long vendorId,
                                  @RequestParam Integer quantity) {

        return deliveryService.createDelivery(productId, vendorId, quantity);
    }

    //update delivery status
    @PutMapping("/status")
    public ProductDelivery updateStatus(@RequestBody UpdateDeliveryStatusRequest request) {
        return deliveryService.updateStatus(
                request.getDeliveryId(),
                request.getStatus()
        );
    }

    //get deliveries of a product
    @GetMapping("/product/{productId}")
    public List<ProductDelivery> getByProduct(@PathVariable Long productId) {
        return deliveryService.getByProduct(productId);
    }

    // get deliveries of vendor
    @GetMapping("/vendor/{vendorId}")
    public List<ProductDelivery> getByVendor(@PathVariable Long vendorId) {
        return deliveryService.getByVendor(vendorId);
    }
}