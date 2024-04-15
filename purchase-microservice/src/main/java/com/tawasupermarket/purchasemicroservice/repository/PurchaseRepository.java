package com.tawasupermarket.purchasemicroservice.repository;

import com.tawasupermarket.purchasemicroservice.model.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseModel, String> {
    List<PurchaseModel> getAllByUserId(String userId);
}
