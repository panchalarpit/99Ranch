package com.tawasupermarket.purchasemicroservice.converter;

import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.model.PurchaseModel;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PurchaseConverter {
    public PurchaseModel convertPurchaseRequestToPurchaseModel(PurchaseRequest purchaseRequest) {
        return PurchaseModel.builder()
                .purchaseAmount(purchaseRequest.getPurchaseAmount())
                .purchaseDate(new Date())
                .build();
    }

    public PurchaseResponse convertPurchaseModelToPurchaseResponse(PurchaseModel purchaseModel) {
        return PurchaseResponse.builder()
                .purchaseId(purchaseModel.getPurchaseId())
                .purchaseAmount(purchaseModel.getPurchaseAmount())
                .purchaseDate(new Date())
                .userId(purchaseModel.getUserId())
                .purchasePoints(purchaseModel.getPurchasePoints())
                .build();
    }
}
