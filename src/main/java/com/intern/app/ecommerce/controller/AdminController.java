package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Admin;
import com.intern.app.ecommerce.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping
    public Admin createAdmin(@Valid @RequestBody Admin admin) {
        //return adminService.createAdmin(admin);


        System.out.println("ADMIN RECEIVED: " + admin.getFirstName() + " " + admin.getEmail());
        return adminService.createAdmin(admin);
    }


    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return "Admin deleted successfully";
    }
    @PutMapping("/{id}")
    public Admin updateAdmin(
            @PathVariable Long id,
            @RequestBody Admin admin) {

        return adminService.updateAdmin(id, admin);
    }



}
