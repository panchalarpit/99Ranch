package com.tawasupermarket.purchasemicroservice.purchase;

import com.tawasupermarket.purchasemicroservice.converter.PurchaseConverter;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.purchasemicroservice.model.PurchaseModel;
import com.tawasupermarket.purchasemicroservice.repository.PurchaseRepository;
import com.tawasupermarket.purchasemicroservice.service.PurchaseServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test purchase service test cases")
@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private PurchaseConverter purchaseConverter;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    private static PurchaseRequest purchaseRequest;
    private static PurchaseResponse purchaseResponse;
    private static PurchaseModel purchaseModel;
    private static List<PurchaseModel> purchaseModels;
    private static List<PurchaseResponse> purchaseResponses;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseServiceImpl(purchaseRepository, purchaseConverter, userService);
    }

    @BeforeAll
    static void beforeAllTestCases() {
        purchaseModel = PurchaseModel.builder().purchaseId("0da93546-4eaa-4331-98e6-738ae14efc34").purchaseDate(new Date())
                .purchasePoints(90).purchaseAmount(120).build();
        purchaseRequest = PurchaseRequest.builder().purchaseAmount(120).build();
        purchaseResponse = PurchaseResponse.builder().purchaseId("0da93546-4eaa-4331-98e6-738ae14efc34").purchaseDate(new Date())
                .purchasePoints(90).purchaseAmount(120).build();
        purchaseModels=new ArrayList<>();
        purchaseModels.add(purchaseModel);
        purchaseResponses =new ArrayList<>();
        purchaseResponses.add(purchaseResponse);
    }

    @Test
    @Order(1)
    @DisplayName("Check retrieving purchase Data By purchaseId")
    public void testGetPurchaseById() {
        Mockito.when(purchaseRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->purchaseService.getPurchaseById("0da93546-4eaa-4331-98e6-738ae14efc34"));
        Mockito.when(purchaseRepository.findById("0da93546-4eaa-4331-98e6-738ae14efc34")).thenReturn(Optional.ofNullable(purchaseModel));
        Mockito.when(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel))
                .thenReturn(purchaseResponse);
        assertNotNull(purchaseService.getPurchaseById("0da93546-4eaa-4331-98e6-738ae14efc34"));
    }

    @Test
    @Order(2)
    @DisplayName("Check to create purchase")
    public void testCreatePurchase() {
        Mockito.when(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel))
                .thenReturn(purchaseResponse);
        Mockito.when(purchaseConverter.convertPurchaseRequestToPurchaseModel(purchaseRequest))
                .thenReturn(purchaseModel);
        Mockito.when(purchaseRepository.save(purchaseModel))
                .thenReturn(purchaseModel);
        assertNotNull(purchaseService.createPurchase(purchaseRequest));
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
