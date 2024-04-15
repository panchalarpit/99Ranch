package com.tawasupermarket.customermicroservice.user;

import com.tawasupermarket.customermicroservice.converter.UserConverter;
import com.tawasupermarket.customermicroservice.dto.request.UserRequest;
import com.tawasupermarket.customermicroservice.dto.response.UserResponse;
import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.exception.UserAlreadyExist;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.repository.UserRepository;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import com.tawasupermarket.customermicroservice.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test User service test cases")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private AESEncryption aesEncryption;
    @InjectMocks
    private UserServiceImpl userService;
    private static UserRequest userRequest;
    private static UserModel userModel;
    private static UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userConverter, aesEncryption);
    }

    @BeforeAll
    static void beforeAllTestCases() {

        userRequest = UserRequest.builder().username("test").userPassword("password").userAddress("city").build();
        userModel = UserModel.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34")
                .username("test").userPassword("password").userRole(UserRole.ROLE_CUSTOMER).userAddress("city").userPoints(0).build();
        userResponse = UserResponse.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34")
                .username("test").userRole(UserRole.ROLE_CUSTOMER).userAddress("city").build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
    public void testUserCreation() {
        Mockito.when(userRepository.getUserModelByUsername("test")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        Mockito.when(userConverter.convertUserRequestToUserModel(userRequest))
                .thenReturn(userModel);
        Mockito.when(userConverter.convertUserModelToUserResponse(userModel))
                .thenReturn(userResponse);
        assertNotNull(userService.createNewUser(userRequest),"User Creation test");
        Mockito.when(userRepository.getUserModelByUsername("test")).thenReturn(Optional.ofNullable(userModel));
        assertThrows(UserAlreadyExist.class, () ->userService.createNewUser(userRequest),"Already User Exists");
    }

    @Test
    @Order(2)
    @DisplayName("Check retrieving user data by userId and getting UserModel")
    public void testUserDataByUserId() {
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(userModel));
        assertNotNull(userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"),"Retrieving user data by UserId");
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"));
    }

    @Test
    @Order(3)
    @DisplayName("Check retrieving user data by userId and getting userResponse Body")
    public void testGetUserByUserId() {
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(userConverter.convertUserModelToUserResponse(userModel))
                .thenReturn(userResponse);
        assertNotNull(userService.getUserByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"),"Retrieving user data by UserId");
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"));
    }

    @Test
    @Order(4)
    @DisplayName("Check user data update")
    public void testUpdateUserData() {
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"));
        Mockito.when(userRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(userConverter.convertUserModelToUserResponse(userModel))
                .thenReturn(userResponse);
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        assertNotNull(userService.updateUserData(userRequest,"0da93546-4eaa-4331-98e6-738ae14efc34"),"Retrieving user data by UserId");
    }

    @Test
    @Order(5)
    @DisplayName("Check retrieving user data by Username")
    public void testGetUserByUsername() {
        Mockito.when(userRepository.getUserModelByUsername("test")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->userService.getUserByUsername("test"));
        Mockito.when(userRepository.getUserModelByUsername("test")).thenReturn(Optional.ofNullable(userModel));
        assertNotNull(userService.getUserByUsername("test"),"Retrieving user data by UserId");
    }
}
