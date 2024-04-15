package com.tawasupermarket.customermicroservice.security.service;

import com.tawasupermarket.customermicroservice.dto.request.LoginRequest;
import com.tawasupermarket.customermicroservice.dto.response.LoginResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.model.UserModel;

import java.nio.file.AccessDeniedException;

public interface AuthService {
    public String generateToken(UserModel User);
    public UserModel validateUser(String JwtToken) throws ResourceNotFoundException, AccessDeniedException;
    public LoginResponse loginValidation(LoginRequest loginRequest) throws AccessDeniedException;
}
