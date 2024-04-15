package com.tawasupermarket.customermicroservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPointRequest {
    @NotNull(message = "userId must not be null")
    private String userId;
    @NotNull(message = "user point must not be null")
    @Min(value = 0, message = "Point must not be less than zero")
    private long userPoint;
}
