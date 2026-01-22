package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    // optional: find images by product id
    List<ProductImage> findByProductId(Long productId);
}

