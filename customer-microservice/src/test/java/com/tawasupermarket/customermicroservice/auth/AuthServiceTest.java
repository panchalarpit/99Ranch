package com.tawasupermarket.customermicroservice.auth;

import com.tawasupermarket.customermicroservice.dto.response.LoginResponse;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.model.UserRole;
import com.tawasupermarket.customermicroservice.security.AESEncryption;
import com.tawasupermarket.customermicroservice.security.JwtUtils;
import com.tawasupermarket.customermicroservice.security.service.AuthServiceImpl;
import com.tawasupermarket.customermicroservice.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test Admin User service test cases")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private AESEncryption aesEncryption;
    @InjectMocks
    private AuthServiceImpl authService;
    private static UserModel userModel;
    private static LoginResponse loginResponse;
    private final static String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZmQwODg1ZC03MDY0LTRjMzAtODYzZi1hNDZmNzk3YjY1ZTUiLCJpYXQiOjE3MTMxNzA2MzEsImV4cCI6MTcxNTMxODExNX0.QLM0vsGXQmPOoqVHDfjq6CeoM_fQ4QJputw2pOfwABE";


    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(jwtUtils, userService, aesEncryption);
    }

    @BeforeAll
    static void beforeAllTestCases() {
        userModel = UserModel.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34")
                .username("test").userPassword("password").userRole(UserRole.ROLE_CUSTOMER).userAddress("city").userPoints(10).build();
        loginResponse = LoginResponse.builder().username("test").token(token).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check generation of token")
    public void testGenerateToken() {
        Mockito.when(jwtUtils.generateToken(userModel)).thenReturn(token);
        assertNotNull(authService.generateToken(userModel));
    }

    @Test
    @Order(2)
    @DisplayName("Check to User creation method")
    public void testValidateUser() throws AccessDeniedException {
        Mockito.when(jwtUtils.validateToken(token)).thenReturn(true);
        Mockito.when(jwtUtils.extractUserId(token)).thenReturn("0da93546-4eaa-4331-98e6-738ae14efc34");
        Mockito.when(userService.getUserDataByUserId("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(userModel);
        assertNotNull(authService.validateUser(token));
        Mockito.when(jwtUtils.validateToken(token)).thenReturn(false);
        assertThrows(AccessDeniedException.class,()->authService.validateUser(token));
    }
}
