package com.tawasupermarket.customermicroservice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.customermicroservice.dto.request.LoginRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.LoginResponse;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.security.service.AuthServiceImpl;
import com.tawasupermarket.customermicroservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private AuthServiceImpl authService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static LoginResponse loginResponse;
    private static LoginRequest loginRequest;
    private static UserResponse userResponse;
    private static UserRequest userRequest;
    private final static String userId = "eaa14fbc-c98c-4519-8dce-0b720bfccbb3";
    private final static String userToken ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlYWExNGZiYy1jOThjLTQ1MTktOGRjZS0wYjcyMGJmY2NiYjMiLCJpYXQiOjE3MTMxMzQyMzAsImV4cCI6MTcxNTI4MTcxNH0.Kqxetda1lTd7nPepPcNd7NdGZgY-4RFvLZpG-8-yYfA";

    @BeforeAll
    static void beforeAllTestCases() {
        loginResponse = LoginResponse.builder().username("test").token("").build();
        loginRequest = LoginRequest.builder().username("test").password("Panchal@123").build();
        userResponse = UserResponse.builder().userId("eaa14fbc-c98c-4519-8dce-0b720bfccbb3").username("test")
                .userAddress("Ahmedabad").userRole(UserRole.ROLE_ADMIN).userPoint(30).build();
        userRequest = UserRequest.builder().username("ArpitPanchal").userPassword("Panchal@123").userAddress("Ahmedabad").build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
    public void checkLogin() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(loginRequest);
        mvc.perform(MockMvcRequestBuilders.post("/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("Check to User creation method")
    public void checkCreateCustomer() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(userRequest);
        mvc.perform(MockMvcRequestBuilders.post("/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
