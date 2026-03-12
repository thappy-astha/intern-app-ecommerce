package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.dto.VendorOrderTrackerResponse;
import com.intern.app.ecommerce.model.*;
import com.intern.app.ecommerce.repository.ProductDeliveryRepository;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import com.intern.app.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductDeliveryService {

    private final ProductDeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    public ProductDeliveryService(ProductDeliveryRepository deliveryRepository,
                                  ProductRepository productRepository,
                                  VendorRepository vendorRepository,
                                  UserRepository userRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
    }

    // create delivery when order placed
    public ProductDelivery createDelivery(Long productId,
                                          Long vendorId,
                                          Long userId,
                                          Integer qty) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        ProductDelivery delivery = new ProductDelivery();
        delivery.setProduct(product);
        delivery.setVendor(vendor);
        delivery.setUser(user);
        delivery.setDeliveredQuantity(qty);
        delivery.setStatus(DeliveryStatus.PLACED);
        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setUpdatedAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }

    // update delivery status
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

    // get deliveries by product
    public List<ProductDelivery> getByProduct(Long productId) {
        return deliveryRepository.findByProductId(productId);
    }

    // old vendor deliveries if needed elsewhere
    public List<ProductDelivery> getRawByVendor(Long vendorId) {
        return deliveryRepository.findByVendorId(vendorId);
    }


    // vendor tracker with price + total
    public List<VendorOrderTrackerResponse> getByVendor(Long vendorId) {
        return deliveryRepository.findVendorOrdersWithPrice(vendorId);
    }
    // get deliveries of a user
    public List<ProductDelivery> getByUser(Long userId) {
        return deliveryRepository.findByUserId(userId);
    }
    }
