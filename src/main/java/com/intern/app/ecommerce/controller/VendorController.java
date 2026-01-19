package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @PostMapping
    public Vendor createVendor(@Valid @RequestBody Vendor vendor) {

        return vendorService.createVendor(vendor);
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
    @PutMapping("/{id}")
    public Vendor updateVendor(
            @PathVariable Long id,
            @Valid @RequestBody Vendor vendor) {

        return vendorService.updateVendor(id, vendor);
    }



}
