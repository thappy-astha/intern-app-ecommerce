package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByVendorId(Long vendorId);
}
