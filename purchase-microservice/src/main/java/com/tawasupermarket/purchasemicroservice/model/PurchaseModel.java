package com.tawasupermarket.purchasemicroservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "purchase")
public class PurchaseModel {
    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String purchaseId;
    @Column(name = "purchase_amount")
    private long purchaseAmount;
    @Column(name = "purchase_date")
    private Date purchaseDate;
    @Column(name = "user_Id")
    private String userId;
    @Column(name = "purchase_points")
    private long purchasePoints;
}
