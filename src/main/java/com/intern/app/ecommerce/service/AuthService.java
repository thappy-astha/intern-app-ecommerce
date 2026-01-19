package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.dto.LoginRequest;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.repository.UserRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    public AuthService(VendorRepository vendorRepository, UserRepository userRepository) {
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
    }

    public String login(LoginRequest request) {

        String role = request.getRole().toUpperCase();

        switch (role) {
            case "VENDOR":
                Vendor vendor = vendorRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Invalid email or role"));
                if (!vendor.getPassword().equals(request.getPassword())) {
                    throw new RuntimeException("Invalid password");
                }
                return "Vendor login successful";

            case "USER":
                User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Invalid email or role"));
                if (!user.getPassword().equals(request.getPassword())) {
                    throw new RuntimeException("Invalid password");
                }
                return "User login successful";

            default:
                throw new RuntimeException("Invalid role. Must be USER or VENDOR");
        }
    }
}

