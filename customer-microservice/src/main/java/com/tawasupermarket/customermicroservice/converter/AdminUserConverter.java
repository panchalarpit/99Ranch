package com.tawasupermarket.customermicroservice.converter;

import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import org.springframework.stereotype.Component;

@Component
public class AdminUserConverter {
    private final AESEncryption aesEncryption;

    public AdminUserConverter(AESEncryption aesEncryption) {
        this.aesEncryption = aesEncryption;
    }

    public UserModel convertAdminUserRequestToUserModel(AdminUserRequest userRequest) {
        return UserModel.builder()
                .username(userRequest.getUsername())
                .userPassword(aesEncryption.encrypt(userRequest.getUserPassword()))
                .userAddress(userRequest.getUserAddress())
                .userRole(userRequest.getUserRole())
                .userPoints(userRequest.getUserPoint())
                .build();
    }

    public AdminUserResponse convertUserModelToAdminUserResponse(UserModel userModel) {
        return AdminUserResponse.builder()
                .userId(userModel.getUserId())
                .username(userModel.getUsername())
                .userAddress(userModel.getUserAddress())
                .userRole(userModel.getUserRole())
                .userPoint(userModel.getUserPoints())
                .build();
    }
}
