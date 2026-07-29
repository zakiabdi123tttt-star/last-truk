package com.truckms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "Magaca macmiilka waa loo baahan yahay")
    private String name;
    private String phoneNumber;
    private String address;
}
