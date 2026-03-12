package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Address;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.repository.AddressRepository;
import com.intern.app.ecommerce.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
//CrossOrigin(origins = "http://localhost:5174")
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressController(AddressRepository addressRepository,
                             UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // ✅ Checkout popup: get all saved addresses for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllUserAddresses(@PathVariable Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        List<Address> addresses = addressRepository.findAllByUser(user);
        return ResponseEntity.ok(addresses);
    }

    // ✅ Add new address for user (HOME / OFFICE / OTHER)
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addUserAddress(@PathVariable Long userId,
                                            @RequestBody Address addressBody) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // create clean entity (avoid user/vendor issues)
        Address a = new Address();
        a.setType(addressBody.getType());
        a.setLine1(addressBody.getLine1());
        a.setLine2(addressBody.getLine2());
        a.setLine3(addressBody.getLine3());
        a.setPinCode(addressBody.getPinCode());
        a.setCity(addressBody.getCity());
        a.setState(addressBody.getState());
        a.setPhoneNumber(addressBody.getPhoneNumber());
        a.setUser(user);

        Address saved = addressRepository.save(a);
        return ResponseEntity.ok(saved);
    }
}