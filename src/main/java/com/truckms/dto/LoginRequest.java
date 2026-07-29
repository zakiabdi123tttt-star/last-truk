package com.truckms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username waa loo baahan yahay")
    private String username;

    @NotBlank(message = "Password waa loo baahan yahay")
    private String password;
}
