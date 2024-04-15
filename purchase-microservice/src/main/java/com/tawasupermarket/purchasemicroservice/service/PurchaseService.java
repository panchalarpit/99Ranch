package com.tawasupermarket.purchasemicroservice.service;

import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;

import java.util.List;

public interface PurchaseService {
    PurchaseResponse getPurchaseById(String purchaseId);

    PurchaseResponse createPurchase(PurchaseRequest purchaseRequest);

    List<PurchaseResponse> getAllPurchaseByUserId();
}
