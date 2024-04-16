package com.tawasupermarket.purchasemicroservice.security.service;

import com.tawasupermarket.purchasemicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.purchasemicroservice.model.UserModel;
import com.tawasupermarket.purchasemicroservice.security.JwtUtils;
import com.tawasupermarket.purchasemicroservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class AuthServiceImpl implements AuthService{

    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    @Autowired
    public AuthServiceImpl(JwtUtils jwtUtils, UserServiceImpl userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

//    To validate user's token
    @Override
    public UserModel validateUser(String jwtToken) throws ResourceNotFoundException, AccessDeniedException {
        if(jwtUtils.validateToken(jwtToken)){
            String userId=jwtUtils.extractUserId(jwtToken);
            AdminUserResponse adminUserResponse= userService.getUserDetails(userId);
            return UserModel.builder()
                    .userId(adminUserResponse.getUserId())
                    .userName(adminUserResponse.getUsername())
                    .userRole(adminUserResponse.getUserRole())
                    .userAddress(adminUserResponse.getUserAddress())
                    .userPoints(adminUserResponse.getUserPoint())
                    .build();
        }
        throw new AccessDeniedException("User invalid");
    }
}
