package com.tawasupermarket.purchasemicroservice.dto.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseResponse {
    private String purchaseId;
    private long purchaseAmount;
    private Date purchaseDate;
    private String userId;
    private long purchasePoints;
}
