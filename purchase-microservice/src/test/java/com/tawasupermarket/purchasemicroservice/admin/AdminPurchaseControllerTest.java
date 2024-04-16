package com.tawasupermarket.purchasemicroservice.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseAdminRequest;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.service.PurchaseAdminServiceImpl;
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
public class AdminPurchaseControllerTest {
    @Mock
    private PurchaseAdminServiceImpl purchaseAdminService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static PurchaseAdminRequest purchaseAdminRequest;
    @Value("${test.admin.userId}")
    private String userId;
    @Value("${test.admin.userToken}")
    private String userToken;

    @BeforeEach
    void beforeTestCases() {
        purchaseAdminRequest = PurchaseAdminRequest.builder().userId(userId).purchaseAmount(120).build();
    }

    @Test
    @Order(1)
    @DisplayName("Check All purchase data by user Id")
    public void checkGetAllPurchaseByUserId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/{userId}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("Check purchase data create")
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
    @DisplayName("Check all purchase data method")
    public void checkGetAllPurchaseData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
