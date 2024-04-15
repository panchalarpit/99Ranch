package com.tawasupermarket.customermicroservice.service;

import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.model.UserModel;

public interface UserService {
    UserResponse createNewUser(UserRequest userRequest);

    UserModel getUserDataByUserId(String userId) throws ResourceNotFoundException;

    UserResponse getUserByUserId(String userId);

    UserResponse updateUserData(UserRequest userRequest, String userId);

    public UserModel getUserByUsername(String username);
}
