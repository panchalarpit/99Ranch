package com.tawasupermarket.customermicroservice.converter;

import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private final AESEncryption aesEncryption;

    public UserConverter(AESEncryption aesEncryption) {
        this.aesEncryption = aesEncryption;
    }

    public UserModel convertUserRequestToUserModel(UserRequest userRequest){
        return UserModel.builder()
                .username(userRequest.getUsername())
                .userPassword(aesEncryption.encrypt(userRequest.getUserPassword()))
                .userAddress(userRequest.getUserAddress())
                .build();
    }

    public UserResponse convertUserModelToUserResponse(UserModel userModel){
        return UserResponse.builder()
                .userId(userModel.getUserId())
                .username(userModel.getUsername())
                .userAddress(userModel.getUserAddress())
                .userRole(userModel.getUserRole())
                .userPoint(userModel.getUserPoints())
                .build();
    }
}
