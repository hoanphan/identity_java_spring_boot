package com.indentity.identity.controller;

import com.indentity.identity.dto.request.UserCreationRequest;
import com.indentity.identity.dto.request.UserUpdateRequest;
import com.indentity.identity.entity.User;
import com.indentity.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    User createUser(@RequestBody UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping()
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(request, userId);
    }

    @DeleteMapping("/{userId}")
    boolean deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(userId);
    }
}
