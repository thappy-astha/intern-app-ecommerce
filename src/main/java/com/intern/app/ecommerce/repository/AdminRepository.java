package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}