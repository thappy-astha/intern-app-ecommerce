package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.dto.VendorOrderTrackerResponse;
import com.intern.app.ecommerce.model.ProductDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDeliveryRepository extends JpaRepository<ProductDelivery, Long> {

    List<ProductDelivery> findByProductId(Long productId);

    List<ProductDelivery> findByVendorId(Long vendorId);

    List<ProductDelivery> findByUserId(Long userId);
    
    @Query("""
                SELECT new com.intern.app.ecommerce.dto.VendorOrderTrackerResponse(
                    pd.id,
                    p.name,
                    pd.deliveredQuantity,
                    p.discountPrice,
                    (p.discountPrice * pd.deliveredQuantity),
                    pd.status,
                    pd.updatedAt
                )
                FROM ProductDelivery pd
                JOIN pd.product p
                WHERE pd.vendor.id = :vendorId
                ORDER BY pd.updatedAt DESC
            """)
    List<VendorOrderTrackerResponse> findVendorOrdersWithPrice(@Param("vendorId") Long vendorId);
}