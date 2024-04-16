package com.tawasupermarket.customermicroservice.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.service.AdminUserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${test.admin.userId}")
    private String userId;
    @Value("${test.admin.userToken}")
    private String userToken;

    @BeforeEach
    void beforeTestCases() {
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
    @DisplayName("Check to User Data")
    public void checkGetUserData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/{userId}",userId)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId));
    }

    @Test
    @Order(3)
    @DisplayName("Check to User data update")
    public void checkUpdateUserData() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(adminUserRequest);
        mvc.perform(MockMvcRequestBuilders.put("/admin/{userId}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId));
    }

    @Test
    @Order(4)
    @DisplayName("Check to User points")
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
    @DisplayName("Check All User Data")
    public void checkGetAllUserData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("Check invalid user data")
    public void checkCreateInvalidAdminUser() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(adminUserInvalidRequest);
        mvc.perform(MockMvcRequestBuilders.post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }
}
