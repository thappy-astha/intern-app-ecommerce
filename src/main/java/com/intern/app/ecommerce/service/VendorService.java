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
    public Vendor updateVendor(Long id, Vendor updatedVendor) {

        Vendor existingVendor = getVendorById(id);

        if (updatedVendor.getProfileImage() != null)
            existingVendor.setProfileImage(updatedVendor.getProfileImage());

        if (updatedVendor.getShopName() != null)
            existingVendor.setShopName(updatedVendor.getShopName());

        if (updatedVendor.getWebsite() != null)
            existingVendor.setWebsite(updatedVendor.getWebsite());

        if (updatedVendor.getPermanentAddress() != null)
            existingVendor.setPermanentAddress(updatedVendor.getPermanentAddress());

        if (updatedVendor.getShopAddress() != null)
            existingVendor.setShopAddress(updatedVendor.getShopAddress());

        if (updatedVendor.getPinCode() != null)
            existingVendor.setPinCode(updatedVendor.getPinCode());

        if (updatedVendor.getPhoneNo() != null)
            existingVendor.setPhoneNo(updatedVendor.getPhoneNo());

        // password optional
        if (updatedVendor.getPassword() != null &&
                updatedVendor.getConfirmPassword() != null) {

            if (!updatedVendor.getPassword().equals(updatedVendor.getConfirmPassword())) {
                throw new RuntimeException("Password mismatch");
            }

            existingVendor.setPassword(updatedVendor.getPassword());
        }

        return vendorRepository.save(existingVendor);
    }



    @Transactional
    public Vendor patchVendor(Long id, Vendor updatedVendor) {

        Vendor existingVendor = getVendorById(id);

        //allowed fields only
        if (updatedVendor.getShopName() != null) {
            existingVendor.setShopName(updatedVendor.getShopName());
        }

        if (updatedVendor.getProfileImage() != null) {
            existingVendor.setProfileImage(updatedVendor.getProfileImage());
        }

        if (updatedVendor.getWebsite() != null) {
            existingVendor.setWebsite(updatedVendor.getWebsite());
        }

        if (updatedVendor.getPhoneNo() != null) {
            existingVendor.setPhoneNo(updatedVendor.getPhoneNo());
        }

        if (updatedVendor.getPermanentAddress() != null) {
            existingVendor.setPermanentAddress(updatedVendor.getPermanentAddress());
        }

        if (updatedVendor.getShopAddress() != null) {
            existingVendor.setShopAddress(updatedVendor.getShopAddress());
        }

        if (updatedVendor.getPinCode() != null) {
            existingVendor.setPinCode(updatedVendor.getPinCode());
        }

        //for password
        if (updatedVendor.getPassword() != null || updatedVendor.getConfirmPassword() != null) {

            if (updatedVendor.getPassword() == null || updatedVendor.getConfirmPassword() == null) {
                throw new RuntimeException("Password & Confirm Password required");
            }

            if (!updatedVendor.getPassword().equals(updatedVendor.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            existingVendor.setPassword(updatedVendor.getPassword());
        }

        return vendorRepository.save(existingVendor);
    }
}