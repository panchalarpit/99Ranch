package com.tawasupermarket.purchasemicroservice.service;

import com.tawasupermarket.purchasemicroservice.dto.response.AdminUserResponse;

import java.nio.file.AccessDeniedException;

public interface UserService {
    AdminUserResponse getUserDetails(String userId) throws AccessDeniedException;
    AdminUserResponse updateUserPoints(long purchasePoints);
}
