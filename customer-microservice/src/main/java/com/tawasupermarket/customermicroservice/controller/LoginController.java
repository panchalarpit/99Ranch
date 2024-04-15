package com.tawasupermarket.customermicroservice.controller;

import com.tawasupermarket.customermicroservice.dto.request.LoginRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.LoginResponse;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.security.service.AuthServiceImpl;
import com.tawasupermarket.customermicroservice.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(value = "/customer")
public class LoginController {

    private final UserServiceImpl userService;
    private final AuthServiceImpl authService;

    @Autowired
    public LoginController(UserServiceImpl userService, AuthServiceImpl authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws AccessDeniedException {
        return new ResponseEntity<>(authService.loginValidation(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createCustomer(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createNewUser(userRequest), HttpStatus.CREATED);
    }

}
