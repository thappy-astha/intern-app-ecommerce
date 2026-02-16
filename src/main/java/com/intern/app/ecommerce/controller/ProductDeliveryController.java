package com.intern.app.ecommerce.controller;

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

    // add delivery
    @PostMapping
    public ProductDelivery createDelivery(
            @RequestParam Long productId,
            @RequestParam Integer deliveredQuantity,
            @RequestParam Boolean delivered
    ) {
        return deliveryService.createDelivery(productId, deliveredQuantity, delivered);
    }

    // get deliveries of a product
    @GetMapping("/product/{productId}")
    public List<ProductDelivery> getByProduct(@PathVariable Long productId) {
        return deliveryService.getByProduct(productId);
    }
}
