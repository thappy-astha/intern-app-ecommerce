package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Address;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.repository.AddressRepository;
import com.intern.app.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository,
                       AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    // ================= CREATE =================
    @Transactional
    public User createUser(User user) {

        if (user.getPassword() == null || user.getConfirmPassword() == null) {
            throw new RuntimeException("Password and Confirm Password are required");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        User savedUser = userRepository.save(user);

        // 🔹 Save address in Address table
        Address addr = new Address();
        addr.setUser(savedUser);
        addr.setType("HOME");
        addr.setLine1(user.getAddressL1());
        addr.setLine2(user.getAddressL2());
        addr.setLine3(user.getAddressL3());
        addr.setPinCode(user.getPinCode());

        addressRepository.save(addr);

        return savedUser;
    }

    // ================= READ =================
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Load address from Address table
        Address addr = addressRepository.findByUserAndType(user, "HOME");
        if (addr != null) {
            user.setAddressL1(addr.getLine1());
            user.setAddressL2(addr.getLine2());
            user.setAddressL3(addr.getLine3());
            user.setPinCode(addr.getPinCode());
        }

        return user;
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // ================= UPDATE =================
    @Transactional
    public User updateUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        if (updatedUser.getAddressL1() != null)
            existingUser.setAddressL1(updatedUser.getAddressL1());

        if (updatedUser.getAddressL2() != null)
            existingUser.setAddressL2(updatedUser.getAddressL2());

        if (updatedUser.getAddressL3() != null)
            existingUser.setAddressL3(updatedUser.getAddressL3());

        if (updatedUser.getPinCode() != null)
            existingUser.setPinCode(updatedUser.getPinCode());

        if (updatedUser.getPhoneNumber() != null)
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // password optional
        if (updatedUser.getPassword() != null ||
                updatedUser.getConfirmPassword() != null) {

            if (updatedUser.getPassword() == null ||
                    updatedUser.getConfirmPassword() == null) {
                throw new RuntimeException("Password and Confirm Password required");
            }

            if (!updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
                throw new RuntimeException("Password mismatch");
            }

            existingUser.setPassword(updatedUser.getPassword());
        }

        User savedUser = userRepository.save(existingUser);

        // 🔹 Update Address table
        Address addr = addressRepository.findByUserAndType(savedUser, "HOME");
        if (addr == null) {
            addr = new Address();
            addr.setUser(savedUser);
            addr.setType("HOME");
        }

        addr.setLine1(updatedUser.getAddressL1());
        addr.setLine2(updatedUser.getAddressL2());
        addr.setLine3(updatedUser.getAddressL3());
        addr.setPinCode(updatedUser.getPinCode());

        addressRepository.save(addr);

        return savedUser;
    }

    // ================= PATCH =================
    @Transactional
    public User patchUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        if (updatedUser.getAddressL1() != null)
            existingUser.setAddressL1(updatedUser.getAddressL1());

        if (updatedUser.getAddressL2() != null)
            existingUser.setAddressL2(updatedUser.getAddressL2());

        if (updatedUser.getAddressL3() != null)
            existingUser.setAddressL3(updatedUser.getAddressL3());

        if (updatedUser.getPinCode() != null)
            existingUser.setPinCode(updatedUser.getPinCode());

        if (updatedUser.getPhoneNumber() != null)
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // Password patch
        if (updatedUser.getPassword() != null || updatedUser.getConfirmPassword() != null) {

            if (updatedUser.getPassword() == null || updatedUser.getConfirmPassword() == null) {
                throw new RuntimeException("Password and Confirm Password are required");
            }

            if (!updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
                throw new RuntimeException("Password and Confirm Password do not match");
            }

            existingUser.setPassword(updatedUser.getPassword());
        }

        User savedUser = userRepository.save(existingUser);

        // 🔹 Patch Address
        Address addr = addressRepository.findByUserAndType(savedUser, "HOME");
        if (addr == null) {
            addr = new Address();
            addr.setUser(savedUser);
            addr.setType("HOME");
        }

        if (updatedUser.getAddressL1() != null)
            addr.setLine1(updatedUser.getAddressL1());

        if (updatedUser.getAddressL2() != null)
            addr.setLine2(updatedUser.getAddressL2());

        if (updatedUser.getAddressL3() != null)
            addr.setLine3(updatedUser.getAddressL3());

        if (updatedUser.getPinCode() != null)
            addr.setPinCode(updatedUser.getPinCode());

        addressRepository.save(addr);

        return savedUser;
    }
}