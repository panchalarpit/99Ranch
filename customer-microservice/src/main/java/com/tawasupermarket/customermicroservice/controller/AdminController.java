package com.tawasupermarket.customermicroservice.controller;

import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.customermicroservice.service.AdminUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminUserServiceImpl userService;

    @Autowired
    public AdminController(AdminUserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<AdminUserResponse> createAdminUser(@Valid @RequestBody AdminUserRequest adminUserRequest) {
        return new ResponseEntity<>(userService.createNewUser(adminUserRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AdminUserResponse> getUserData(@Valid @PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.getUserDataByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AdminUserResponse> updateUserData(@Valid @PathVariable("userId") String userId,
                                                            @Valid @RequestBody AdminUserRequest adminUserRequest) {
        return new ResponseEntity<>(userService.updateUserData(adminUserRequest, userId), HttpStatus.OK);
    }

    @PostMapping("/updatePoint")
    public ResponseEntity<AdminUserResponse> updateUserPoint(@Valid @RequestBody UserPointRequest userPointRequest) {
        return new ResponseEntity<>(userService.updateUserPoints(userPointRequest), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUserData() {
        return new ResponseEntity<>(userService.getAllUserData(), HttpStatus.OK);
    }

}
