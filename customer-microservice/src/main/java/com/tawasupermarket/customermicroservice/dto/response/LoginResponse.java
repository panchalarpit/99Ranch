package com.tawasupermarket.customermicroservice.dto.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private String username;
    private String token;
}
