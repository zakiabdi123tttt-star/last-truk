package com.truckms.service;

import com.truckms.dto.DashboardResponse;
import com.truckms.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TripRepository tripRepository;

    public DashboardResponse getDashboard() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        long totalTrips = tripRepository.count();
        BigDecimal totalIncome = tripRepository.sumIncomeBetween(LocalDate.of(2000, 1, 1), LocalDate.of(2100, 1, 1));
        BigDecimal totalExpenses = tripRepository.sumExpenseBetween(LocalDate.of(2000, 1, 1), LocalDate.of(2100, 1, 1));
        BigDecimal profit = totalIncome.subtract(totalExpenses);

        long deliveredToday = tripRepository.countByTripDate(today);

        BigDecimal monthlyIncome = tripRepository.sumIncomeBetween(startOfMonth, endOfMonth);
        BigDecimal monthlyExpenses = tripRepository.sumExpenseBetween(startOfMonth, endOfMonth);

        // Chart: dhawaan 6 bilood ee la soo dhaafay - Income vs Expense
        Map<String, BigDecimal> incomeVsExpense = new LinkedHashMap<>();
        Map<String, Long> tripsPerMonth = new LinkedHashMap<>();

        for (int i = 5; i >= 0; i--) {
            YearMonth ym = YearMonth.from(today).minusMonths(i);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();
            String label = ym.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + ym.getYear();

            BigDecimal income = tripRepository.sumIncomeBetween(start, end);
            incomeVsExpense.put(label + " (Income)", income);
            BigDecimal expense = tripRepository.sumExpenseBetween(start, end);
            incomeVsExpense.put(label + " (Expense)", expense);

            tripsPerMonth.put(label, tripRepository.countByTripDateBetween(start, end));
        }

        List<Object> recent = List.copyOf(tripRepository.findTop10ByOrderByTripDateDescIdDesc());

        return DashboardResponse.builder()
                .totalTrips(totalTrips)
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .profit(profit)
                .materialsDeliveredToday(deliveredToday)
                .monthlyIncome(monthlyIncome)
                .monthlyExpenses(monthlyExpenses)
                .incomeVsExpenseChart(incomeVsExpense)
                .tripsPerMonthChart(tripsPerMonth)
                .recentTrips(recent)
                .build();
    }
}
