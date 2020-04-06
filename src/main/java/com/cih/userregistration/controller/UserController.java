package com.cih.userregistration.controller;

import com.cih.userregistration.entities.User;
import com.cih.userregistration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public final UserService userService;

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public User updateUserById(@RequestBody User user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping
    public List<User> findByLastName(@RequestParam(value = "lastName", required = false) String lastName) {
        return userService.findByLastName(lastName);
    }

    @GetMapping("/page/{pageNumber}/")
    public List<User> findByFilter(@RequestParam(value = "firstName", required = false) String firstName,
                                   @RequestParam(value = "lastName", required = false) String lastName,
                                   @RequestParam(value = "address", required = false) String address,
                                   @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                   @RequestParam(value = "email", required = false) String email,
                                   @PathVariable int pageNumber) {
        User filteredUser = User.builder()
                .lastName(lastName)
                .firstName(firstName)
                .phoneNumber(phoneNumber)
                .address(address)
                .email(email)
                .build();
        return userService.findByFilter(filteredUser, pageNumber);
    }
}