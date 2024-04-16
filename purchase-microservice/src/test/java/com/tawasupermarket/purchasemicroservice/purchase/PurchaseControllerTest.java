package com.tawasupermarket.purchasemicroservice.purchase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.service.PurchaseServiceImpl;
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
public class PurchaseControllerTest {
    @Mock
    private PurchaseServiceImpl purchaseService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static PurchaseRequest purchaseRequest;
    private final static String purchaseId = "4d1c14ff-913c-489c-9365-564579afe904";
    @Value("${test.userId}")
    private String userId ;
    @Value("${test.userToken}")
    private String userToken;

    @BeforeEach
    void beforeTestCases() {
        purchaseRequest = PurchaseRequest.builder().purchaseAmount(120).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to purchase creation method")
    public void checkCreatePurchase() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(purchaseRequest);
        mvc.perform(MockMvcRequestBuilders.post("/purchase/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchasePoints").value(90));
    }

    @Test
    @Order(2)
    @DisplayName("Check to purchase data method")
    public void checkGetPurchaseData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/purchase/{purchaseId}",purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchasePoints").value(90));
    }

    @Test
    @Order(3)
    @DisplayName("Check retrieving all user data method")
    public void checkGetAllPurchaseData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/purchase/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
