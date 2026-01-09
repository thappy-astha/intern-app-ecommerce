package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
}
