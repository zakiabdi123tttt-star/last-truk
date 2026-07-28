package com.truckms.dto;

import com.truckms.entity.PaymentStatus;
import com.truckms.entity.TripUnit;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TripRequest {
    @NotNull(message = "Taariikhda waa loo baahan yahay")
    private LocalDate tripDate;

    // Ikhtiyaari - spec update: Truck field waa laga saaray form-ka Trip-ka
    private Integer truckId;

    @NotNull(message = "Material-ka waa loo baahan yahay")
    private Integer materialId;

    @NotNull(message = "Macmiilka waa loo baahan yahay")
    private Integer customerId;

    @NotNull(message = "Quantity waa loo baahan yahay")
    private BigDecimal quantity;

    private TripUnit unit;
    private String deliveryLocation;

    @NotNull(message = "Customer Charge waa loo baahan yahay")
    private BigDecimal customerCharge;

    private BigDecimal expenseAmount;
    private PaymentStatus paymentStatus;
    private BigDecimal amountPaid;
    private String notes;
}
