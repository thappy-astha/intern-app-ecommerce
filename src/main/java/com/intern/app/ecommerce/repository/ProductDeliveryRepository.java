package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.ProductDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDeliveryRepository extends JpaRepository<ProductDelivery, Long> {

    List<ProductDelivery> findByProductId(Long productId);

    List<ProductDelivery> findByVendorId(Long vendorId);
}