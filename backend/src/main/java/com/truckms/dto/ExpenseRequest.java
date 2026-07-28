package com.truckms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    @NotNull(message = "Taariikhda waa loo baahan yahay")
    private LocalDate expenseDate;

    @NotBlank(message = "Nooca kharashka waa loo baahan yahay")
    private String expenseType;

    @NotNull(message = "Amount-ka waa loo baahan yahay")
    private BigDecimal amount;

    private String description;
    private Integer truckId;
    private Integer tripId;
}
