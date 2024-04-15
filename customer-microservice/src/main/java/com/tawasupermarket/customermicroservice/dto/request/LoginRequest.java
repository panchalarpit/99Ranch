package com.tawasupermarket.customermicroservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 25, message = "Username must be between 3 to 25 character")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must contain only letters, digits, or underscores")
    private String username;
    @NotNull(message = "password must not be null")
    @Size(min = 8, max = 30, message = "Password must be between 8 to 30 character")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one digit, one letter, and one special character")
    private String password;
}
