package com.intern.app.ecommerce.repository;

import com.intern.app.ecommerce.model.Address;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByUserAndType(User user, String type);

    Address findByVendorAndType(Vendor vendor, String type);
}