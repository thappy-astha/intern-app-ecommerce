package com.intern.app.ecommerce.dto;

public class UserPatchRequest {

    private Long phoneNumber;
    private String addressL1;
    private String addressL2;
    private String addressL3;
    private Long pinCode;

    public Long getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(Long phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddressL1() { return addressL1; }
    public void setAddressL1(String addressL1) { this.addressL1 = addressL1; }

    public String getAddressL2() { return addressL2; }
    public void setAddressL2(String addressL2) { this.addressL2 = addressL2; }

    public String getAddressL3() { return addressL3; }
    public void setAddressL3(String addressL3) { this.addressL3 = addressL3; }

    public Long getPinCode() { return pinCode; }
    public void setPinCode(Long pinCode) { this.pinCode = pinCode; }
}