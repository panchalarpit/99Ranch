package com.tawasupermarket.customermicroservice.dto.response;

import com.tawasupermarket.customermicroservice.model.UserRole;
import lombok.*;

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
