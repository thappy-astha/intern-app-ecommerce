package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.Address;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // existing (keep)
    Address findByUserAndType(User user, String type);
    Address findByVendorAndType(Vendor vendor, String type);

    // ✅ new (for checkout popup list)
    List<Address> findAllByUser(User user);
    List<Address> findAllByUserAndType(User user, String type);

    List<Address> findAllByVendor(Vendor vendor);
    List<Address> findAllByVendorAndType(Vendor vendor, String type);
}