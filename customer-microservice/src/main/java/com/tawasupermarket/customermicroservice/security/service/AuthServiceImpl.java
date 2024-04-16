package com.tawasupermarket.customermicroservice.security.service;

import com.tawasupermarket.customermicroservice.dto.request.LoginRequest;
import com.tawasupermarket.customermicroservice.dto.response.LoginResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import com.tawasupermarket.customermicroservice.security.JwtUtils;
import com.tawasupermarket.customermicroservice.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;
    private final AESEncryption aesEncryption;

    public AuthServiceImpl(JwtUtils jwtUtils, UserServiceImpl userService, AESEncryption aesEncryption) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.aesEncryption = aesEncryption;
    }

//    To generate token using user's information and application secret
    @Override
    public String generateToken(UserModel user) {
        return jwtUtils.generateToken(user);
    }

//    To validate user's token using application secret
    @Override
    public UserModel validateUser(String jwtToken) throws ResourceNotFoundException, AccessDeniedException {
        if (jwtUtils.validateToken(jwtToken)) {
            String userId = jwtUtils.extractUserId(jwtToken);
            return userService.getUserDataByUserId(userId);
        }
        throw new AccessDeniedException("User invalid");
    }

    @Override
    public LoginResponse loginValidation(LoginRequest loginRequest) throws AccessDeniedException {
        UserModel userModel = userService.getUserByUsername(loginRequest.getUsername());
        if (userModel.getUserPassword().equals(aesEncryption.encrypt(loginRequest.getPassword()))) {
            return LoginResponse.builder()
                    .username(userModel.getUsername())
                    .token(generateToken(userModel))
                    .build();
        }
        throw new AccessDeniedException("Username or Password is not correct");
    }
}
