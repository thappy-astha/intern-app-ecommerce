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

    // ================= REGISTER =================
    public User registerUser(User user) {

        // duplicate checks
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone number already registered");
        }

        // password validation
        if (user.getPassword() == null || user.getConfirmPassword() == null) {
            throw new RuntimeException("Password and Confirm Password are required");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User saved = userRepository.save(user);

        saveOrUpdateAddress(saved, user);

        return mapAddressToUser(saved);
    }

    // ================= GET ALL =================
    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll();

        users.forEach(this::mapAddressToUser);

        return users;
    }

    // ================= GET BY ID =================
    public User getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapAddressToUser(user);
    }

    // ================= DELETE =================
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

        if (updatedUser.getFirstName() != null)
            existingUser.setFirstName(updatedUser.getFirstName());

        if (updatedUser.getLastName() != null)
            existingUser.setLastName(updatedUser.getLastName());

        if (updatedUser.getPhoneNumber() != null)
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // password optional
        if (updatedUser.getPassword() != null &&
                updatedUser.getConfirmPassword() != null) {

            if (!updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
                throw new RuntimeException("Password mismatch");
            }

            existingUser.setPassword(updatedUser.getPassword());
        }

        User saved = userRepository.save(existingUser);

        saveOrUpdateAddress(saved, updatedUser);

        return mapAddressToUser(saved);
    }

    // ================= PATCH =================
    @Transactional
    public User patchUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        if (updatedUser.getFirstName() != null)
            existingUser.setFirstName(updatedUser.getFirstName());

        if (updatedUser.getLastName() != null)
            existingUser.setLastName(updatedUser.getLastName());

        if (updatedUser.getPhoneNumber() != null)
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // password
        if (updatedUser.getPassword() != null || updatedUser.getConfirmPassword() != null) {

            if (updatedUser.getPassword() == null || updatedUser.getConfirmPassword() == null) {
                throw new RuntimeException("Password & Confirm Password required");
            }

            if (!updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            existingUser.setPassword(updatedUser.getPassword());
        }

        User saved = userRepository.save(existingUser);

        saveOrUpdateAddress(saved, updatedUser);

        return mapAddressToUser(saved);
    }

    // ================= ADDRESS SAVE =================
    private void saveOrUpdateAddress(User user, User src) {

        if (src.getAddressL1() != null || src.getPinCode() != null) {

            Address addr = addressRepository.findByUserAndType(user, "HOME");

            if (addr == null) {
                addr = new Address();
                addr.setUser(user);
                addr.setType("HOME");
            }

            addr.setLine1(src.getAddressL1());
            addr.setLine2(src.getAddressL2());
            addr.setLine3(src.getAddressL3());
            addr.setPinCode(src.getPinCode());

            addressRepository.save(addr);
        }
    }

    // ================= ADDRESS MAP =================
    private User mapAddressToUser(User user) {

        Address addr = addressRepository.findByUserAndType(user, "HOME");

        if (addr != null) {

            user.setAddressL1(addr.getLine1());
            user.setAddressL2(addr.getLine2());
            user.setAddressL3(addr.getLine3());
            user.setPinCode(addr.getPinCode());
        }

        return user;
    }
}