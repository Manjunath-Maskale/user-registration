package com.cih.userregistration.controller;

import com.cih.userregistration.entities.User;
import com.cih.userregistration.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(value = "User info")
public class UserController {

    public final UserService userService;


    @ApiOperation(value="Get a list of all users")
    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @ApiOperation(value="Add a user")
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @ApiOperation("Find a specific user by their Id")
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @ApiOperation("Update a specific user, if they exist, by their Id otherwise add the user")
    @PutMapping("/{id}")
    public User updateUserById(@RequestBody User user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @ApiOperation("Delete a specific user by their Id")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }


    @ApiOperation("Filter all users by any parameter, page number required")
    @GetMapping("/")
    public List<User> findByFilter(@RequestParam(value = "firstName", required = false) String firstName,
                                   @RequestParam(value = "lastName", required = false) String lastName,
                                   @RequestParam(value = "address", required = false) String address,
                                   @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                   @RequestParam(value = "email", required = false) String email,
                                   @RequestParam(value = "pageNumber") int pageNumber) {
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