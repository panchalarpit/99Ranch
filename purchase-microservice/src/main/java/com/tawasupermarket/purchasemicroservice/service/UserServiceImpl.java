package com.tawasupermarket.purchasemicroservice.service;

import com.tawasupermarket.purchasemicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final WebClient webClient;
    @Value("${customer.microservice.url}")
    private String baseUrl;
    @Value("${jwt.admin.token}")
    private String adminToken;

    @Autowired
    public UserServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    private String getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return (String) request.getSession().getAttribute("userId");
    }

    @Override
    public AdminUserResponse getUserDetails(String userId) throws AccessDeniedException {
        ResponseEntity<AdminUserResponse> responseEntity = webClient.get()
                .uri(baseUrl + "admin/{userId}", userId)
                .header("Authorization", adminToken)
                .retrieve()
                .toEntity(AdminUserResponse.class)
                .block();
        if(Objects.nonNull(responseEntity.getBody()))
            return responseEntity.getBody();
        throw new ResourceNotFoundException("User not found");
    }

    @Override
    public AdminUserResponse updateUserPoints(long purchasePoints) {
        ResponseEntity<AdminUserResponse> responseEntity = webClient.post()
                .uri(baseUrl + "admin/updatePoint")
                .header("Authorization", adminToken)
                .bodyValue(UserPointRequest.builder()
                        .userId(getCurrentUserId())
                        .userPoint(purchasePoints).build())
                .retrieve()
                .toEntity(AdminUserResponse.class)
                .block();
        if(Objects.nonNull(responseEntity.getBody()))
            return responseEntity.getBody();
        throw new ResourceNotFoundException("User not found");
    }
}
