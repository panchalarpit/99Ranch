package com.tawasupermarket.customermicroservice.dto.response;

import com.tawasupermarket.customermicroservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserResponse {
    private String userId;
    private String username;
    private String userAddress;
    private UserRole userRole;
    private long userPoint;
}
