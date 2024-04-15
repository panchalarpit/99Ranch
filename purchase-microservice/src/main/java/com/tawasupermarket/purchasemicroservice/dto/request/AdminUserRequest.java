package com.tawasupermarket.purchasemicroservice.dto.request;

import com.tawasupermarket.purchasemicroservice.model.UserRole;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserRequest {
    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 25, message = "Username must be between 3 to 25 character")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must contain only letters, digits, or underscores")
    private String username;
    @NotNull(message = "password must not be null")
    @Size(min = 8, max = 30, message = "Password must be between 8 to 30 character")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one digit, one letter, and one special character")
    private String userPassword;
    @NotNull(message = "address must not be null")
    @Size(min = 3, max = 50, message = "Address must be between 3 to 50 character")
    private String userAddress;
    @NotNull(message = "Role must not be null")
    private UserRole userRole;
    @NotNull(message = "user point must not be null")
    @Min(value = 0, message = "Point must not be less than zero")
    private long userPoint;
}
