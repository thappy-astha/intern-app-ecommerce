package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.AddToCartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddToCartRepository extends JpaRepository<AddToCartDetails, Long> {

    Optional<AddToCartDetails> findByUserIdAndProductId(Long userId, Long productId);

    List<AddToCartDetails> findByUserId(Long userId);
}