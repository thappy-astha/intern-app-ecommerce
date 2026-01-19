package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.repository.VendorRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Added for safety

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor createVendor(Vendor vendor) {
        if (vendor.getPassword() == null || vendor.getConfirmPassword() == null) {
            throw new RuntimeException("Password and Confirm Password are required");
        }

        if (!vendor.getPassword().equals(vendor.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public void deleteVendor(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found");
        }
        vendorRepository.deleteById(id);
    }

    @Transactional
    public Vendor updateVendor(Long id, @Nonnull Vendor updatedVendor) {
        Vendor existingVendor = getVendorById(id);


        existingVendor.setFirstName(updatedVendor.getFirstName());
        existingVendor.setMiddleName(updatedVendor.getMiddleName());
        existingVendor.setLastName(updatedVendor.getLastName());
        existingVendor.setGender(updatedVendor.getGender());
        existingVendor.setEmail(updatedVendor.getEmail());
        existingVendor.setAddressL1(updatedVendor.getAddressL1());
        existingVendor.setAddressL2(updatedVendor.getAddressL2());
        existingVendor.setAddressL3(updatedVendor.getAddressL3());
        existingVendor.setPinCode(updatedVendor.getPinCode());
        existingVendor.setPhoneNumber(updatedVendor.getPhoneNumber());
        existingVendor.setPassword(updatedVendor.getPassword());
        existingVendor.setRole(updatedVendor.getRole());

        //  Update password ONLY if provided
        if (updatedVendor.getPassword() != null && updatedVendor.getConfirmPassword() != null) {

            if (!updatedVendor.getPassword().equals(updatedVendor.getConfirmPassword())) {
                throw new RuntimeException("Password and Confirm Password do not match");
            }

            existingVendor.setPassword(updatedVendor.getPassword());
        }
        return vendorRepository.save(existingVendor);
    }
}