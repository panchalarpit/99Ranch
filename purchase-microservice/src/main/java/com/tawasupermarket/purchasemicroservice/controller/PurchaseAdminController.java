package com.tawasupermarket.purchasemicroservice.controller;

import com.tawasupermarket.purchasemicroservice.dto.request.PurchaseAdminRequest;
import com.tawasupermarket.purchasemicroservice.dto.response.PurchaseResponse;
import com.tawasupermarket.purchasemicroservice.service.PurchaseAdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/admin")
public class PurchaseAdminController {
    private final PurchaseAdminServiceImpl purchaseAdminService;

    @Autowired
    public PurchaseAdminController(PurchaseAdminServiceImpl purchaseAdminService) {
        this.purchaseAdminService = purchaseAdminService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PurchaseResponse>> getAllPurchaseByUserId(@Valid @PathVariable("userId") String userId) {
        return new ResponseEntity<>(purchaseAdminService.getAllPurchaseByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PurchaseResponse> createPurchaseByUserId(@Valid PurchaseAdminRequest purchaseAdminRequest) {
        return new ResponseEntity<>(purchaseAdminService.createPurchase(purchaseAdminRequest), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PurchaseResponse>> getAllPurchaseData() {
        return new ResponseEntity<>(purchaseAdminService.getAllPurchaseData(), HttpStatus.OK);
    }

}
