package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.dto.LoginRequest;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.repository.UserRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    public AuthService(VendorRepository vendorRepository, UserRepository userRepository) {
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
    }

    public Object login(LoginRequest request) {

        String role = request.getRole().toUpperCase();

        switch (role) {
            case "VENDOR":
                Vendor vendor = vendorRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Invalid email or role"));
                if (!vendor.getPassword().equals(request.getPassword())) {
                    throw new RuntimeException("Invalid password");
                }
                Map<String, Object> vendorResponse = new HashMap<>();
                vendorResponse.put("id", vendor.getId());
                vendorResponse.put("firstname", vendor.getFirstName());   // or firstName
                vendorResponse.put("email", vendor.getEmail());
                vendorResponse.put("role", "VENDOR");

                return vendorResponse;

            case "USER":
                User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Invalid email or role"));
                if (!user.getPassword().equals(request.getPassword())) {
                    throw new RuntimeException("Invalid password");
                }
                Map<String, Object> userResponse = new HashMap<>();
                userResponse.put("id", user.getId());
                userResponse.put("firstName", user.getFirstName());
                userResponse.put("email", user.getEmail());
                userResponse.put("role", "USER");

                return userResponse;

            default:
                throw new RuntimeException("Invalid role. Must be USER or VENDOR");
        }
    }
}

