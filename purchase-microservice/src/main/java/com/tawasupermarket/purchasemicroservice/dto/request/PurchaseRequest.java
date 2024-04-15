package com.tawasupermarket.purchasemicroservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseRequest {
    @NotNull(message = "purchase amount must not be null")
    @Min(value = 0, message = "amount must not be less than zero")
    private long purchaseAmount;
}
