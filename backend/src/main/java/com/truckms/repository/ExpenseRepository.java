package com.truckms.repository;

import com.truckms.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByExpenseDateBetween(LocalDate start, LocalDate end);
    List<Expense> findByTripId(Integer tripId);
    List<Expense> findByTruckId(Integer truckId);
}
