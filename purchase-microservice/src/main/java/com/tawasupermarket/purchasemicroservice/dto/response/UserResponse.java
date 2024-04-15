package com.tawasupermarket.purchasemicroservice.dto.response;

import com.tawasupermarket.purchasemicroservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String userId;
    private String username;
    private String userAddress;
    private UserRole userRole;
    private long userPoint;
}
