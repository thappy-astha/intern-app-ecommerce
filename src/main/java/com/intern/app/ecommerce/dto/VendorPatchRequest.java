package com.intern.app.ecommerce.dto;

public class VendorPatchRequest {

    private String shopName;
    private String website;
    private String shopAddress;
    private Long phoneNo;
    private String profileImage;

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getShopAddress() { return shopAddress; }
    public void setShopAddress(String shopAddress) { this.shopAddress = shopAddress; }

    public Long getPhoneNo() { return phoneNo; }
    public void setPhoneNo(Long phoneNo) { this.phoneNo = phoneNo; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
}

