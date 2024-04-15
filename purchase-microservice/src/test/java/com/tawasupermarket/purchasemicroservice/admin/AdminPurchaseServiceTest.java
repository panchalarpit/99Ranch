package com.tawasupermarket.purchasemicroservice.admin;

import com.tawasupermarket.purchasemicroservice.converter.PurchaseConverter;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseAdminRequest;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.model.PurchaseModel;
import com.tawasupermarket.purchasemicroservice.repository.PurchaseRepository;
import com.tawasupermarket.purchasemicroservice.service.PurchaseAdminServiceImpl;
import com.tawasupermarket.purchasemicroservice.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test purchase service test cases")
@ExtendWith(MockitoExtension.class)
public class AdminPurchaseServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private PurchaseConverter purchaseConverter;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private PurchaseAdminServiceImpl purchaseService;
    private static PurchaseResponse purchaseResponse;
    private static PurchaseRequest purchaseRequest;
    private static PurchaseAdminRequest purchaseAdminRequest;
    private static List<PurchaseResponse> purchaseResponses;
    private static PurchaseModel purchaseModel;
    private static List<PurchaseModel> purchaseModels;


    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseAdminServiceImpl(purchaseRepository, purchaseConverter, userService);
    }

    @BeforeAll
    static void beforeAllTestCases() {
        purchaseModel = PurchaseModel.builder().purchaseId("0da93546-4eaa-4331-98e6-738ae14efc34").purchaseDate(new Date())
                .purchasePoints(90).purchaseAmount(120).build();
        purchaseAdminRequest = PurchaseAdminRequest.builder().userId("0da93546-4eaa-4331-98e6-738ae14efc34").purchaseAmount(120).build();
        purchaseResponse = PurchaseResponse.builder().purchaseId("0da93546-4eaa-4331-98e6-738ae14efc34").purchaseDate(new Date())
                .purchasePoints(90).purchaseAmount(120).build();
        purchaseRequest = PurchaseRequest.builder().purchaseAmount(120).build();
        purchaseModels=new ArrayList<>();
        purchaseModels.add(purchaseModel);
        purchaseResponses =new ArrayList<>();
        purchaseResponses.add(purchaseResponse);
    }

    @Test
    @Order(1)
    @DisplayName("Check to create purchase")
    public void testCreatePurchase() {
        Mockito.when(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel))
                .thenReturn(purchaseResponse);
        Mockito.when(purchaseConverter.convertPurchaseRequestToPurchaseModel(any()))
                .thenReturn(purchaseModel);
        Mockito.when(purchaseRepository.save(purchaseModel))
                .thenReturn(purchaseModel);
        assertNotNull(purchaseService.createPurchase(purchaseAdminRequest));
    }

    @Test
    @Order(2)
    @DisplayName("Check retrieving purchase Data By userId")
    public void testGetAllPurchaseByUserId() {
        Mockito.when(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel))
                .thenReturn(purchaseResponse);
        Mockito.when(purchaseRepository.getAllByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"))
                .thenReturn(purchaseModels);
        assertNotNull(purchaseService.getAllPurchaseByUserId("0da93546-4eaa-4331-98e6-738ae14efc34"));
        assertFalse(purchaseService.getAllPurchaseByUserId("0da93546-4eaa-4331-98e6-738ae14efc34").isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Check retrieving all purchase Data")
    public void testGetAllPurchaseData() {
        Mockito.when(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel))
                .thenReturn(purchaseResponse);
        Mockito.when(purchaseRepository.findAll())
                .thenReturn(purchaseModels);
        assertNotNull(purchaseService.getAllPurchaseData());
        assertFalse(purchaseService.getAllPurchaseData().isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Checking point calculation")
    public void testCalculatePoints() {
        assertEquals(0,purchaseService.calculatePoints(0));
        assertEquals(0,purchaseService.calculatePoints(-12));
        assertEquals(0,purchaseService.calculatePoints(23));
        assertEquals(10,purchaseService.calculatePoints(60));
        assertEquals(90,purchaseService.calculatePoints(120));
    }
}
