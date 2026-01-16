package com.intern.app.ecommerce.controller;


import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Log the incoming object to see if fields like firstName or addressL1 are null
        logger.info("Received signup request for: {}", user.getEmail());
        logger.info("AddressL1 received: {}", user.getAddressL1());

        User savedUser = userService.saveUser(user);

        logger.info("User saved successfully with ID: {}", savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }


}