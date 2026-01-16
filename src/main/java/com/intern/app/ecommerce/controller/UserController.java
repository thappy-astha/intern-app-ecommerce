package com.intern.app.ecommerce.controller;


import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
//    public User createUser(@Valid @RequestBody User user) {
//        return userService.createUser(user);

    public User createUser(@RequestBody User user) {
        System.out.println("🔥 POST /api/user HIT 🔥");
        System.out.println("User: " + user.getFirstName() + " " + user.getEmail());
        return userService.createUser(user);
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