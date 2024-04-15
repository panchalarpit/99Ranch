package com.tawasupermarket.customermicroservice.service;

import com.tawasupermarket.customermicroservice.converter.UserConverter;
import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.exception.UserAlreadyExist;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.repository.UserRepository;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AESEncryption aesEncryption;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, AESEncryption aesEncryption) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.aesEncryption = aesEncryption;
    }

    @Override
    public UserResponse createNewUser(UserRequest userRequest) {
        if (userRepository.getUserModelByUsername(userRequest.getUsername()).isEmpty()) {
            UserModel userModel = userConverter.convertUserRequestToUserModel(userRequest);
            userModel.setUserRole(UserRole.ROLE_CUSTOMER);
            userModel.setUserPoints(0);
            return userConverter.convertUserModelToUserResponse(userRepository.save(userModel));
        } else {
            throw new UserAlreadyExist("User " + userRequest.getUsername() + " already Exists");
        }
    }

    @Override
    public UserModel getUserDataByUserId(String userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserResponse getUserByUserId(String userId) {
        return userConverter.convertUserModelToUserResponse(userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found")));
    }

    @Override
    public UserResponse updateUserData(UserRequest userRequest, String userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userModel.setUserAddress(userRequest.getUserAddress());
        userModel.setUserPassword(aesEncryption.encrypt(userRequest.getUserPassword()));
        return userConverter.convertUserModelToUserResponse(userRepository.save(userModel));
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userRepository.getUserModelByUsername(username).orElseThrow(()->new ResourceNotFoundException("User Not found"));
    }
}
