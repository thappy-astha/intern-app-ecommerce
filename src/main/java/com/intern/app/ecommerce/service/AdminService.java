package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Admin;
import com.intern.app.ecommerce.repository.AdminRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Added for safety

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found");
        }
        adminRepository.deleteById(id);
    }

    @Transactional
    public Admin updateAdmin(Long id, @Nonnull Admin updatedAdmin) {
        Admin existingAdmin = getAdminById(id);


        existingAdmin.setFirstName(updatedAdmin.getFirstName());
        existingAdmin.setMiddleName(updatedAdmin.getMiddleName());
        existingAdmin.setLastName(updatedAdmin.getLastName());
        existingAdmin.setGender(updatedAdmin.getGender());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setAddressL1(updatedAdmin.getAddressL1());
        existingAdmin.setAddressL2(updatedAdmin.getAddressL2());
        existingAdmin.setAddressL3(updatedAdmin.getAddressL3());
        existingAdmin.setPinCode(updatedAdmin.getPinCode());
        existingAdmin.setPhoneNumber(updatedAdmin.getPhoneNumber());
        existingAdmin.setPassword(updatedAdmin.getPassword());
        existingAdmin.setRole(updatedAdmin.getRole());

        return adminRepository.save(existingAdmin);
    }
}