package com.tawasupermarket.customermicroservice.admin;

import com.tawasupermarket.customermicroservice.converter.AdminUserConverter;
import com.tawasupermarket.customermicroservice.dto.request.AdminUserRequest;
import com.tawasupermarket.customermicroservice.dto.request.UserPointRequest;
import com.tawasupermarket.customermicroservice.dto.response.AdminUserResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.exception.UserAlreadyExist;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.repository.UserRepository;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import com.tawasupermarket.customermicroservice.service.AdminUserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test Admin User service test cases")
@ExtendWith(MockitoExtension.class)
public class AdminUserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdminUserConverter userConverter;
    @Mock
    private AESEncryption aesEncryption;
    @InjectMocks
    private AdminUserServiceImpl userService;
    private static AdminUserRequest adminUserRequest;
    private static AdminUserResponse adminUserResponse;
    private static UserModel userModel;
    private static UserPointRequest userPointRequest;
    private static List<UserModel> userModels;
    private static List<AdminUserResponse> adminUserResponses;

    @BeforeEach
    void setUp() {
        userService = new AdminUserServiceImpl(userRepository, userConverter, aesEncryption);
    }

    @BeforeAll
    static void beforeAllTestCases() {
        adminUserRequest = AdminUserRequest.builder().username("test").userPassword("password").userAddress("city")
                .userRole(UserRole.ROLE_ADMIN).userPoint(10).build();
        userModel = UserModel.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34")
                .username("test").userPassword("password").userRole(UserRole.ROLE_CUSTOMER).userAddress("city").userPoints(10).build();
        adminUserResponse = AdminUserResponse.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34")
                .username("test").userAddress("city")
                .userRole(UserRole.ROLE_ADMIN).userPoint(10).build();
        adminUserResponses = new ArrayList<>();
        adminUserResponses.add(adminUserResponse);
        userModels = new ArrayList<>();
        userModels.add(userModel);
        userPointRequest = UserPointRequest.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34").userPoint(10).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
    public void testUserCreation() {
        Mockito.when(userRepository.getUserModelByUsername("test")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        Mockito.when(userConverter.convertAdminUserRequestToUserModel(adminUserRequest))
                .thenReturn(userModel);
        Mockito.when(userConverter.convertUserModelToAdminUserResponse(userModel))
                .thenReturn(adminUserResponse);
        assertNotNull(userService.createNewUser(adminUserRequest),"User Creation test");
        Mockito.when(userRepository.getUserModelByUsername("test")).thenReturn(Optional.ofNullable(userModel));
        assertThrows(UserAlreadyExist.class, () -> userService.createNewUser(adminUserRequest),"Already User Exists");
    }

    @Test
    @Order(2)
    @DisplayName("Check retrieving  User by userId")
    public void testGetUserDataByUserId() {
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"),"User not found");
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(userConverter.convertUserModelToAdminUserResponse(userModel))
                .thenReturn(adminUserResponse);
        assertNotNull(userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"),"Retrieving user data by UserId");

    }

    @Test
    @Order(3)
    @DisplayName("Check updating user data")
    public void testUpdateUserData() {
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.updateUserData(adminUserRequest,"0da93546-4eaa-4331-98e6-738ae14efc34"),"User not found");
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        Mockito.when(userConverter.convertUserModelToAdminUserResponse(userModel))
                .thenReturn(adminUserResponse);
        assertNotNull(userService.updateUserData(adminUserRequest,"0da93546-4eaa-4331-98e6-738ae14efc34"),"Retrieving user data by UserId");

    }

    @Test
    @Order(4)
    @DisplayName("Check to All User Data")
    public void getAllUserData() {
        Mockito.when(userRepository.findAll()).thenReturn(userModels);
        Mockito.when(userConverter.convertUserModelToAdminUserResponse(userModel))
                .thenReturn(adminUserResponse);
        assertNotNull(userService.getAllUserData(),"User Creation test");
        assertFalse(adminUserResponses.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Check User point updates")
    public void getUpdateUserPoints() {
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.updateUserPoints(userPointRequest),"User not found");
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(userConverter.convertUserModelToAdminUserResponse(userModel))
                .thenReturn(adminUserResponse);
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        assertNotNull(userService.updateUserPoints(userPointRequest),"Retrieving user data by UserId");
        assertEquals(userService.updateUserPoints(userPointRequest).getUserPoint(),10);
    }

}
