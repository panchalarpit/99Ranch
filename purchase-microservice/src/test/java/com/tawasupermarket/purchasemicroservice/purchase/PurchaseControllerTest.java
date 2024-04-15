package com.tawasupermarket.purchasemicroservice.purchase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.service.PurchaseServiceImpl;
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
public class PurchaseControllerTest {
    @Mock
    private PurchaseServiceImpl purchaseService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static PurchaseRequest purchaseRequest;
    private final static String purchaseId = "4d1c14ff-913c-489c-9365-564579afe904";
    private final static String userId = "afd0885d-7064-4c30-863f-a46f797b65e5";
    private final static String userToken ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZmQwODg1ZC03MDY0LTRjMzAtODYzZi1hNDZmNzk3YjY1ZTUiLCJpYXQiOjE3MTMxNzA2MzEsImV4cCI6MTcxNTMxODExNX0.QLM0vsGXQmPOoqVHDfjq6CeoM_fQ4QJputw2pOfwABE";


    @BeforeAll
    static void beforeAllTestCases() {
        purchaseRequest = PurchaseRequest.builder().purchaseAmount(120).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
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
    @DisplayName("Check to User creation method")
    public void checkGetPurchaseData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/purchase/{purchaseId}",purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchasePoints").value(90));
    }

    @Test
    @Order(3)
    @DisplayName("Check to User creation method")
    public void checkGetAllPurchaseData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/purchase/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
