
package com.intern.app.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

    @Entity
    @Table(name = "vendor")
    public class Vendor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Column(name = "first_name", nullable = false)
        private String firstName;

        @NotBlank
        @Column(name = "last_name", nullable = false)
        private String lastName;

        @NotBlank
        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @NotBlank
        @Column(name = "gender", nullable = false)
        private String gender;

        @NotBlank
        @Column(name = "shop_name", nullable = false)
        private String shopName;

        @Column(name = "website")
        private String website;

        @NotBlank
        @Column(name = "permanent_address", nullable = false)
        private String permanentAddress;

        @NotBlank
        @Column(name = "shop_address", nullable = false)
        private String shopAddress;

        @Column(name = "pin_code", nullable = false)
        private Long pinCode;

        @Column(name = "phone_no", nullable = false)
        private Long phoneNo;

        @Column(name = "gst_number")
        private String gstNumber;

        @NotBlank
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(name = "password", nullable = false)
        private String password;

        // NOT stored in DB
        @Transient
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String confirmPassword;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Column(name = "role", nullable = false)
        private String role = "VENDOR";


        public Vendor() {}



        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public String getShopName() { return shopName; }
        public void setShopName(String shopName) { this.shopName = shopName; }

        public String getWebsite() { return website; }
        public void setWebsite(String website) { this.website = website; }

        public String getPermanentAddress() { return permanentAddress; }
        public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }

        public String getShopAddress() { return shopAddress; }
        public void setShopAddress(String shopAddress) { this.shopAddress = shopAddress; }

        public Long getPinCode() { return pinCode; }
        public void setPinCode(Long pinCode) { this.pinCode = pinCode; }

        public Long getPhoneNo() { return phoneNo; }
        public void setPhoneNo(Long phoneNo) { this.phoneNo = phoneNo; }

        public String getGstNumber() { return gstNumber; }
        public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getConfirmPassword() { return confirmPassword; }
        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

        public String getRole() { return role; }
    }
