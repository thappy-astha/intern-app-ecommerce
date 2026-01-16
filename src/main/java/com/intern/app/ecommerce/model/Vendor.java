package com.intern.app.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "vendors") // Maps to the admins table in Railway
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address_l1", nullable = false)
    private String addressL1;

    @Column(name = "address_l2")
    private String addressL2;

    @Column(name = "address_l3")
    private String addressL3;


    @Column(name = "pin_code", nullable = false)
    private Long pinCode;

    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "role", nullable = false)
    private String role;

    public Vendor() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddressL1() { return addressL1; }
    public void setAddressL1(String addressL1) { this.addressL1 = addressL1; }

    public String getAddressL2() { return addressL2; }
    public void setAddressL2(String addressL2) { this.addressL2 = addressL2; }

    public String getAddressL3() { return addressL3; }
    public void setAddressL3(String addressL3) { this.addressL3 = addressL3; }

    public Long getPinCode() { return pinCode; }
    public void setPinCode(Long pinCode) { this.pinCode = pinCode; }

    public Long getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(Long phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}