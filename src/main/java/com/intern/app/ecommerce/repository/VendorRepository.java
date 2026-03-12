package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Optional: check if phone number is unique too
    boolean existsByPhoneNo(String phoneNo);
}