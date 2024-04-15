package com.tawasupermarket.purchasemicroservice.service;

import com.tawasupermarket.purchasemicroservice.converter.PurchaseConverter;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseAdminRequest;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.model.PurchaseModel;
import com.tawasupermarket.purchasemicroservice.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseAdminServiceImpl implements PurchaseAdminService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseConverter purchaseConverter;
    private final UserServiceImpl userService;

    @Autowired
    public PurchaseAdminServiceImpl(PurchaseRepository purchaseRepository, PurchaseConverter purchaseConverter, UserServiceImpl userService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseConverter = purchaseConverter;
        this.userService = userService;
    }

    @Override
    public List<PurchaseResponse> getAllPurchaseByUserId(String userId) {
        List<PurchaseModel> purchaseModels = purchaseRepository.getAllByUserId(userId);
        List<PurchaseResponse> purchaseResponses = new ArrayList<>();
        for (PurchaseModel purchaseModel : purchaseModels) {
            purchaseResponses.add(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel));
        }
        return purchaseResponses;
    }

    @Override
    public PurchaseResponse createPurchase(PurchaseAdminRequest purchaseAdminRequest) {
        PurchaseModel purchaseModel = purchaseConverter.convertPurchaseRequestToPurchaseModel(
                PurchaseRequest.builder().purchaseAmount(purchaseAdminRequest.getPurchaseAmount()).build()
        );
        purchaseModel.setUserId(purchaseAdminRequest.getUserId());
        long purchasePoint = calculatePoints(purchaseAdminRequest.getPurchaseAmount());
        purchaseModel.setPurchasePoints(purchasePoint);
        userService.updateUserPoints(purchasePoint);
        return purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseRepository.save(purchaseModel));
    }

    @Override
    public List<PurchaseResponse> getAllPurchaseData() {
        List<PurchaseModel> purchaseModels = purchaseRepository.findAll();
        List<PurchaseResponse> purchaseResponses = new ArrayList<>();
        for (PurchaseModel purchaseModel : purchaseModels) {
            purchaseResponses.add(purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseModel));
        }
        return purchaseResponses;
    }

    public long calculatePoints(long purchaseAmount) {
        if (purchaseAmount < 50) {
            return 0;
        } else if (purchaseAmount > 50 && purchaseAmount < 101) {
            return purchaseAmount - 50;
        } else {
            return 50 + ((purchaseAmount - 100) * 2);
        }
    }
}
