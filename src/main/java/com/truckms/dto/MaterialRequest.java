package com.truckms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialRequest {
    @NotBlank(message = "Magaca material-ka waa loo baahan yahay")
    private String name;
}
