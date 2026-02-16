package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.ProductDelivery;
import com.intern.app.ecommerce.repository.ProductDeliveryRepository;
import com.intern.app.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductDeliveryService {

    private final ProductDeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;

    public ProductDeliveryService(ProductDeliveryRepository deliveryRepository,
                                  ProductRepository productRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productRepository = productRepository;
    }

    public ProductDelivery createDelivery(Long productId,
                                          Integer deliveredQty,
                                          Boolean delivered) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDelivery delivery = new ProductDelivery();
        delivery.setProduct(product);
        delivery.setDeliveredQuantity(deliveredQty);
        delivery.setDelivered(delivered);
        delivery.setDeliveryDate(LocalDate.now());

        // ⭐ AUTO STOCK UPDATE
        if (Boolean.TRUE.equals(delivered)) {
            int newQty = product.getQuantity() - deliveredQty;
            if (newQty < 0) newQty = 0;
            product.setQuantity(newQty);
            productRepository.save(product);
        }

        return deliveryRepository.save(delivery);
    }

    public List<ProductDelivery> getByProduct(Long productId) {
        return deliveryRepository.findByProductId(productId);
    }
}
