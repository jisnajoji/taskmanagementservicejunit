package com.example.tms.controller;

import com.example.tms.dao.UserRequest;
import com.example.tms.models.User;
import com.example.tms.service.user.UserServiceImpl;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest user) throws BadRequestException {
        return ResponseEntity.ok(userService.createUser(user));
    }
}

