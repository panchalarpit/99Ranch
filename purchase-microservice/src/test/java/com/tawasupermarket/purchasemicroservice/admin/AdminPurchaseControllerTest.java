package com.tawasupermarket.purchasemicroservice.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseAdminRequest;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.service.PurchaseAdminServiceImpl;
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
public class AdminPurchaseControllerTest {
    @Mock
    private PurchaseAdminServiceImpl purchaseAdminService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static PurchaseAdminRequest purchaseAdminRequest;
    private final static String userId = "afd0885d-7064-4c30-863f-a46f797b65e5";
    private final static String userToken ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlYWExNGZiYy1jOThjLTQ1MTktOGRjZS0wYjcyMGJmY2NiYjMiLCJpYXQiOjE3MTMxMzQyMzAsImV4cCI6MTcxNTI4MTcxNH0.Kqxetda1lTd7nPepPcNd7NdGZgY-4RFvLZpG-8-yYfA";

    @BeforeAll
    static void beforeAllTestCases() {
        purchaseAdminRequest = PurchaseAdminRequest.builder().userId(userId).purchaseAmount(120).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check to User creation method")
    public void checkGetAllPurchaseByUserId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/{userId}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("Check to User creation method")
    public void checkCreatePurchaseByUserId() throws Exception {
        String requestBodyJson = objectMapper.writeValueAsString(purchaseAdminRequest);
        mvc.perform(MockMvcRequestBuilders.post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content(requestBodyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(3)
    @DisplayName("Check to User creation method")
    public void checkGetAllPurchaseData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
