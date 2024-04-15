package com.tawasupermarket.purchasemicroservice.service;

import com.tawasupermarket.purchasemicroservice.converter.PurchaseConverter;
import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.purchasemicroservice.model.PurchaseModel;
import com.tawasupermarket.purchasemicroservice.repository.PurchaseRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseConverter purchaseConverter;
    private final UserServiceImpl userService;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseConverter purchaseConverter,
                               UserServiceImpl userService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseConverter = purchaseConverter;
        this.userService = userService;
    }

    private String getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return (String) request.getSession().getAttribute("userId");
    }

    @Override
    public PurchaseResponse getPurchaseById(String purchaseId) {
        return purchaseConverter.convertPurchaseModelToPurchaseResponse(
                purchaseRepository.findById(purchaseId).orElseThrow(() -> new ResourceNotFoundException("Purchase not found")));
    }

    @Override
    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest) {
        PurchaseModel purchaseModel = purchaseConverter.convertPurchaseRequestToPurchaseModel(purchaseRequest);
        purchaseModel.setUserId(getCurrentUserId());
        long purchasePoint = calculatePoints(purchaseRequest.getPurchaseAmount());
        purchaseModel.setPurchasePoints(purchasePoint);
        userService.updateUserPoints(purchasePoint);
        return purchaseConverter.convertPurchaseModelToPurchaseResponse(purchaseRepository.save(purchaseModel));
    }

    @Override
    public List<PurchaseResponse> getAllPurchaseByUserId() {
        List<PurchaseModel> purchaseModels = purchaseRepository.getAllByUserId(getCurrentUserId());
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
