package com.tawasupermarket.customermicroservice.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.model.UserRole;
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
public class UserControllerTest {
    @Mock
    private UserServiceImpl userService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static UserResponse userResponse;
    private static UserRequest userRequest;
    private static UserRequest userInvalidRequest;
    private final static String userId = "afd0885d-7064-4c30-863f-a46f797b65e5";
    private final static String userToken ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZmQwODg1ZC03MDY0LTRjMzAtODYzZi1hNDZmNzk3YjY1ZTUiLCJpYXQiOjE3MTMxNzA2MzEsImV4cCI6MTcxNTMxODExNX0.QLM0vsGXQmPOoqVHDfjq6CeoM_fQ4QJputw2pOfwABE";

    @BeforeAll
    static void beforeAllTestCases() {
        userRequest = UserRequest.builder().username("Arpit").userPassword("Panchal@123").userAddress("Ahmedabad").build();
        userInvalidRequest = UserRequest.builder().username("Arpit").userPassword("Panchal").userAddress("Ahmedabad").build();
        userResponse = UserResponse.builder().userId(userId)
                .username("Arpit").userRole(UserRole.ROLE_CUSTOMER).userAddress("Ahmedabad").build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
    public void checkGetUserData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{userId}", userResponse.getUserId())
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    @Order(2)
    @DisplayName("Check to User update")
    public void checkUpdateUserData() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(userRequest);
        mvc.perform(MockMvcRequestBuilders.put("/user/{userId}", userResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    @Order(3)
    @DisplayName("Check to User update")
    public void checkInvalidUpdateUserData() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(userInvalidRequest);
        mvc.perform(MockMvcRequestBuilders.put("/user/{userId}", userResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }
}
