package com.truckms.dto;

import com.truckms.entity.TruckStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TruckRequest {
    @NotBlank(message = "Truck number waa loo baahan yahay")
    private String truckNumber;
    private String plateNumber;
    private String driverName;
    private TruckStatus status;
}
