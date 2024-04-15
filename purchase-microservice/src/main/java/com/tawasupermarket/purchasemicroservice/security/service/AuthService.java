package com.tawasupermarket.purchasemicroservice.security.service;

import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.purchasemicroservice.model.UserModel;

import java.nio.file.AccessDeniedException;

public interface AuthService {
    public UserModel validateUser(String JwtToken) throws ResourceNotFoundException, AccessDeniedException;
}
