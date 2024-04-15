package com.tawasupermarket.purchasemicroservice.controller;

import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.service.PurchaseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseServiceImpl purchaseService;

    @Autowired
    public PurchaseController(PurchaseServiceImpl purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/create")
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest) {
        return new ResponseEntity<>(purchaseService.createPurchase(purchaseRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseResponse> getPurchaseData(@Valid @PathVariable("purchaseId") String purchaseId) {
        return new ResponseEntity<>(purchaseService.getPurchaseById(purchaseId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PurchaseResponse>> getAllPurchaseData() {
        return new ResponseEntity<>(purchaseService.getAllPurchaseByUserId(), HttpStatus.OK);
    }

}
