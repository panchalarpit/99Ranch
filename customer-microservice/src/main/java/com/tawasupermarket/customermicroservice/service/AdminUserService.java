package com.tawasupermarket.customermicroservice.service;

import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;

import java.util.List;

public interface AdminUserService {

    AdminUserResponse createNewUser(AdminUserRequest userRequest);
    AdminUserResponse getUserDataByUserId(String userId);
    AdminUserResponse updateUserData(AdminUserRequest userRequest, String userId);
    List<AdminUserResponse> getAllUserData();
    AdminUserResponse updateUserPoints(UserPointRequest userPointRequest);

}
