package com.tawasupermarket.customermicroservice.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.service.AdminUserServiceImpl;
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
public class AdminUserControllerTest {
    @Mock
    private AdminUserServiceImpl userService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static AdminUserResponse adminUserResponse;
    private static AdminUserRequest adminUserNewRequest;
    private static AdminUserRequest adminUserRequest;
    private static AdminUserRequest adminUserInvalidRequest;
    private static UserPointRequest userPointRequest;
    private final static String userId = "eaa14fbc-c98c-4519-8dce-0b720bfccbb3";
    private final static String userToken ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlYWExNGZiYy1jOThjLTQ1MTktOGRjZS0wYjcyMGJmY2NiYjMiLCJpYXQiOjE3MTMxMzQyMzAsImV4cCI6MTcxNTI4MTcxNH0.Kqxetda1lTd7nPepPcNd7NdGZgY-4RFvLZpG-8-yYfA";

    @BeforeAll
    static void beforeAllTestCases() {
        adminUserResponse = AdminUserResponse.builder().userId(userId).username("test").userAddress("Ahmedabad")
                .userRole(UserRole.ROLE_ADMIN).userPoint(0).build();
        adminUserRequest = AdminUserRequest.builder().username("test").userAddress("Ahmedabad").userPassword("Panchal@123")
                .userRole(UserRole.ROLE_ADMIN).userPoint(0).build();
        adminUserNewRequest = AdminUserRequest.builder().username("ArpitPanchal").userAddress("Ahmedabad").userPassword("Panchal@123")
                .userRole(UserRole.ROLE_ADMIN).userPoint(0).build();
        adminUserInvalidRequest = AdminUserRequest.builder().username("test").userAddress("Ahmedabad").userPassword("Panchal")
                .userRole(UserRole.ROLE_ADMIN).userPoint(0).build();
        userPointRequest = UserPointRequest.builder().userId(userId).userPoint(10).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
    public void checkCreateAdminUser() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(adminUserNewRequest);
        mvc.perform(MockMvcRequestBuilders.post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(adminUserResponse.getUsername()));
    }

    @Test
    @Order(2)
    @DisplayName("Check to User creation method")
    public void checkGetUserData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/{userId}",userId)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test"));
    }

    @Test
    @Order(3)
    @DisplayName("Check to User creation method")
    public void checkUpdateUserData() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(adminUserRequest);
        mvc.perform(MockMvcRequestBuilders.put("/admin/{userId}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test"));
    }

    @Test
    @Order(4)
    @DisplayName("Check to User creation method")
    public void checkUpdateUserPoint() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(userPointRequest);
        mvc.perform(MockMvcRequestBuilders.post("/admin/updatePoint",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId));
    }

    @Test
    @Order(5)
    @DisplayName("Check to User creation method")
    public void checkGetAllUserData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("Check to User creation method")
    public void checkCreateInvalidAdminUser() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(adminUserInvalidRequest);
        mvc.perform(MockMvcRequestBuilders.post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }
}
