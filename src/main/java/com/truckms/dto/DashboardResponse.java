package com.truckms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private long totalTrips;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal profit;
    private long materialsDeliveredToday;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private Map<String, BigDecimal> incomeVsExpenseChart;
    private Map<String, Long> tripsPerMonthChart;
    private List<Object> recentTrips;
}
