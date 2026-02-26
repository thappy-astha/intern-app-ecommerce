package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Address;
import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.repository.AddressRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final AddressRepository addressRepository;

    public VendorService(VendorRepository vendorRepository,
                         AddressRepository addressRepository) {
        this.vendorRepository = vendorRepository;
        this.addressRepository = addressRepository;
    }

    // ================= CREATE =================
    public Vendor createVendor(Vendor vendor) {

        if (vendor.getPassword() == null || vendor.getConfirmPassword() == null) {
            throw new RuntimeException("Password and Confirm Password are required");
        }

        if (!vendor.getPassword().equals(vendor.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        Vendor saved = vendorRepository.save(vendor);

        saveOrUpdateAddresses(saved, vendor);

        return mapAddressesToVendor(saved);
    }

    // ================= GET ALL =================
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        vendors.forEach(this::mapAddressesToVendor);
        return vendors;
    }

    // ================= GET BY ID =================
    public Vendor getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return mapAddressesToVendor(vendor);
    }

    // ================= DELETE =================
    public void deleteVendor(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found");
        }
        vendorRepository.deleteById(id);
    }

    // ================= UPDATE =================
    @Transactional
    public Vendor updateVendor(Long id, Vendor updatedVendor) {

        Vendor existingVendor = getVendorById(id);

        if (updatedVendor.getShopName() != null)
            existingVendor.setShopName(updatedVendor.getShopName());

        if (updatedVendor.getWebsite() != null)
            existingVendor.setWebsite(updatedVendor.getWebsite());

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

        Vendor saved = vendorRepository.save(existingVendor);

        // 🔹 UPDATE ADDRESSES
        saveOrUpdateAddresses(saved, updatedVendor);

        return mapAddressesToVendor(saved);
    }

    // ================= PATCH =================
    @Transactional
    public Vendor patchVendor(Long id, Vendor updatedVendor) {

        Vendor existingVendor = getVendorById(id);

        if (updatedVendor.getShopName() != null)
            existingVendor.setShopName(updatedVendor.getShopName());

        if (updatedVendor.getWebsite() != null)
            existingVendor.setWebsite(updatedVendor.getWebsite());

        if (updatedVendor.getPhoneNo() != null)
            existingVendor.setPhoneNo(updatedVendor.getPhoneNo());

        // password
        if (updatedVendor.getPassword() != null || updatedVendor.getConfirmPassword() != null) {

            if (updatedVendor.getPassword() == null || updatedVendor.getConfirmPassword() == null) {
                throw new RuntimeException("Password & Confirm Password required");
            }

            if (!updatedVendor.getPassword().equals(updatedVendor.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            existingVendor.setPassword(updatedVendor.getPassword());
        }

        Vendor saved = vendorRepository.save(existingVendor);

        // 🔹 PATCH ADDRESSES
        saveOrUpdateAddresses(saved, updatedVendor);

        return mapAddressesToVendor(saved);
    }

    // ================= ADDRESS SAVE =================
    private void saveOrUpdateAddresses(Vendor vendor, Vendor src) {

        if (src.getPermanentAddress() != null || src.getPinCode() != null) {

            Address perm = addressRepository.findByVendorAndType(vendor, "PERMANENT");
            if (perm == null) {
                perm = new Address();
                perm.setVendor(vendor);
                perm.setType("PERMANENT");
            }

            perm.setLine1(src.getPermanentAddress());
            perm.setPinCode(src.getPinCode());
            addressRepository.save(perm);
        }

        if (src.getShopAddress() != null || src.getPinCode() != null) {

            Address shop = addressRepository.findByVendorAndType(vendor, "SHOP");
            if (shop == null) {
                shop = new Address();
                shop.setVendor(vendor);
                shop.setType("SHOP");
            }

            shop.setLine1(src.getShopAddress());
            shop.setPinCode(src.getPinCode());
            addressRepository.save(shop);
        }
    }

    // ================= ADDRESS MAP =================
    private Vendor mapAddressesToVendor(Vendor vendor) {

        Address perm = addressRepository.findByVendorAndType(vendor, "PERMANENT");
        if (perm != null) {
            vendor.setPermanentAddress(perm.getLine1());
            vendor.setPinCode(perm.getPinCode());
        }

        Address shop = addressRepository.findByVendorAndType(vendor, "SHOP");
        if (shop != null) {
            vendor.setShopAddress(shop.getLine1());
        }

        return vendor;
    }
}