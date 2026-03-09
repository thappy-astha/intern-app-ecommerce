package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }




    @PostMapping
    public ResponseEntity<?> createVendor(@Valid @RequestBody Vendor vendor) {
        try {
            Vendor saved = vendorService.registerVendor(vendor); // uses duplicate check
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }
    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }


    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return "Vendor deleted successfully";
    }


    @PutMapping("/update/{id}")
    public Vendor updateVendor(
            @PathVariable Long id,
            @RequestBody Vendor vendor) {

        return vendorService.updateVendor(id, vendor);
    }

    @PatchMapping("/update/{id}")
    public Vendor patchVendor(@PathVariable Long id,
                              @RequestBody Vendor vendor) {
        return vendorService.patchVendor(id, vendor);
    }



}
