package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.*;
import com.intern.app.ecommerce.repository.ProductDeliveryRepository;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductDeliveryService {

    private final ProductDeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;

    public ProductDeliveryService(ProductDeliveryRepository deliveryRepository,
                                  ProductRepository productRepository,
                                  VendorRepository vendorRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }

    // create delivery when order placed
    public ProductDelivery createDelivery(Long productId,
                                          Long vendorId,
                                          Integer qty) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        ProductDelivery delivery = new ProductDelivery();
        delivery.setProduct(product);
        delivery.setVendor(vendor);
        delivery.setDeliveredQuantity(qty);
        delivery.setStatus(DeliveryStatus.PLACED);
        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setUpdatedAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }

    //update delivery status
    public ProductDelivery updateStatus(Long deliveryId,
                                        DeliveryStatus status) {

        ProductDelivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        delivery.setStatus(status);
        delivery.setUpdatedAt(LocalDateTime.now());

        // reduce stock only when delivered
        if (status == DeliveryStatus.DELIVERED) {

            Product product = delivery.getProduct();
            int deliveredQty = delivery.getDeliveredQuantity();

            if (deliveredQty > product.getQuantity()) {
                throw new RuntimeException("Delivered quantity exceeds stock");
            }

            product.setQuantity(product.getQuantity() - deliveredQty);
            productRepository.save(product);
        }

        return deliveryRepository.save(delivery);
    }

    //get deliveries by product
    public List<ProductDelivery> getByProduct(Long productId) {
        return deliveryRepository.findByProductId(productId);
    }

    // vendor deliveries
    public List<ProductDelivery> getByVendor(Long vendorId) {
        return deliveryRepository.findByVendorId(vendorId);
    }
}