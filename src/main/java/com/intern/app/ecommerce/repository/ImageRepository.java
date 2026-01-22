package com.intern.app.ecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



    @Repository
    public interface ImageRepository extends JpaRepository<Object, Long> {

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO image (product_id, image) VALUES (:productId, :image)",
                nativeQuery = true)
        void saveImage(@Param("productId") Long productId,
                       @Param("image") byte[] image);
    }


