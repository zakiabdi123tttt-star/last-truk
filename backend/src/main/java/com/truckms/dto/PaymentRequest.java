package com.truckms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequest {
    @NotNull(message = "Trip-ka waa loo baahan yahay")
    private Integer tripId;

    @NotNull(message = "Amount-ka waa loo baahan yahay")
    private BigDecimal amount;

    private LocalDate paymentDate;
    private String notes;
}
