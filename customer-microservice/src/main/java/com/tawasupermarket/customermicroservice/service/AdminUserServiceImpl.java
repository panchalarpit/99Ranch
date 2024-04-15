package com.tawasupermarket.customermicroservice.service;

import com.tawasupermarket.customermicroservice.converter.AdminUserConverter;
import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.exception.UserAlreadyExist;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.repository.UserRepository;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final AdminUserConverter adminUserConverter;
    private final AESEncryption aesEncryption;

    @Autowired
    public AdminUserServiceImpl(UserRepository userRepository, AdminUserConverter adminUserConverter, AESEncryption aesEncryption) {
        this.userRepository = userRepository;
        this.adminUserConverter = adminUserConverter;
        this.aesEncryption = aesEncryption;
    }

    @Override
    public AdminUserResponse createNewUser(AdminUserRequest userRequest) {
        if (userRepository.getUserModelByUsername(userRequest.getUsername()).isEmpty()) {
            UserModel userModel = adminUserConverter.convertAdminUserRequestToUserModel(userRequest);
            return adminUserConverter.convertUserModelToAdminUserResponse(userRepository.save(userModel));
        }
        throw new UserAlreadyExist("User " + userRequest.getUsername() + " already Exists");
    }

    @Override
    public AdminUserResponse getUserDataByUserId(String userId) {
        return adminUserConverter.convertUserModelToAdminUserResponse(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")));
    }

    @Override
    public AdminUserResponse updateUserData(AdminUserRequest userRequest, String userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userModel.setUserAddress(userRequest.getUserAddress());
        userModel.setUserPassword(aesEncryption.encrypt(userRequest.getUserPassword()));
        userModel.setUserRole(userRequest.getUserRole());
        userModel.setUserPoints(userModel.getUserPoints());
        return adminUserConverter.convertUserModelToAdminUserResponse(userRepository.save(userModel));
    }

    @Override
    public List<AdminUserResponse> getAllUserData() {
        List<UserModel> userModels = userRepository.findAll();
        List<AdminUserResponse> userResponses = new ArrayList<>();
        for (UserModel userModel : userModels) {
            userResponses.add(adminUserConverter.convertUserModelToAdminUserResponse(userModel));
        }
        return userResponses;
    }

    @Override
    public AdminUserResponse updateUserPoints(UserPointRequest userPointRequest) {
        UserModel userModel=userRepository.findById(userPointRequest.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userModel.setUserPoints(userModel.getUserPoints()+userPointRequest.getUserPoint());
        return adminUserConverter.convertUserModelToAdminUserResponse(userRepository.save(userModel));
    }
}
