package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

            if (user.getPassword() == null || user.getConfirmPassword() == null) {
                throw new RuntimeException("Password and Confirm Password are required");
            }

            if (!user.getPassword().equals(user.getConfirmPassword())) {
                throw new RuntimeException("Password and Confirm Password do not match");
            }

            return userRepository.save(user);
        }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }


        @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setMiddleName(updatedUser.getMiddleName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddressL1(updatedUser.getAddressL1());
        existingUser.setAddressL2(updatedUser.getAddressL2());
        existingUser.setAddressL3(updatedUser.getAddressL3());
        existingUser.setPinCode(updatedUser.getPinCode());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPassword(updatedUser.getPassword());

            //Update password ONLY if provided
            if (updatedUser.getPassword() != null && updatedUser.getConfirmPassword() != null) {

                if (!updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
                    throw new RuntimeException("Password and Confirm Password do not match");
                }

                existingUser.setPassword(updatedUser.getPassword());
            }


            return userRepository.save(existingUser);
    }


    @Transactional
    public User patchUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getMiddleName() != null) {
            existingUser.setMiddleName(updatedUser.getMiddleName());
        }

        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getAddressL1() != null) {
            existingUser.setAddressL1(updatedUser.getAddressL1());
        }

        if (updatedUser.getAddressL2() != null) {
            existingUser.setAddressL2(updatedUser.getAddressL2());
        }

        if (updatedUser.getAddressL3() != null) {
            existingUser.setAddressL3(updatedUser.getAddressL3());
        }

        if (updatedUser.getPinCode() != null) {
            existingUser.setPinCode(updatedUser.getPinCode());
        }

        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        // 🔐 Password patch (only if both provided)
        if (updatedUser.getPassword() != null || updatedUser.getConfirmPassword() != null) {

            if (updatedUser.getPassword() == null || updatedUser.getConfirmPassword() == null) {
                throw new RuntimeException("Password and Confirm Password are required");
            }

            if (!updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
                throw new RuntimeException("Password and Confirm Password do not match");
            }

            existingUser.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(existingUser);
    }



}