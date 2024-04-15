package com.tawasupermarket.purchasemicroservice.service;

import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseAdminRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;

import java.util.List;

public interface PurchaseAdminService {
    List<PurchaseResponse> getAllPurchaseByUserId(String userId);
    PurchaseResponse createPurchase(PurchaseAdminRequest purchaseAdminRequest);
    List<PurchaseResponse> getAllPurchaseData();
}
