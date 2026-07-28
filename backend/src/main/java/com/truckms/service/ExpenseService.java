package com.truckms.service;

import com.truckms.dto.ExpenseRequest;
import com.truckms.entity.Expense;
import com.truckms.repository.ExpenseRepository;
import com.truckms.repository.TripRepository;
import com.truckms.repository.TruckRepository;
import com.truckms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TruckRepository truckRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public List<Expense> findByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findByExpenseDateBetween(start, end);
    }

    public Expense create(ExpenseRequest req) {
        Expense expense = Expense.builder()
                .expenseDate(req.getExpenseDate())
                .expenseType(req.getExpenseType())
                .amount(req.getAmount())
                .description(req.getDescription())
                .recordedBy(currentUser())
                .build();

        if (req.getTruckId() != null) {
            expense.setTruck(truckRepository.findById(req.getTruckId()).orElse(null));
        }
        if (req.getTripId() != null) {
            expense.setTrip(tripRepository.findById(req.getTripId()).orElse(null));
        }

        return expenseRepository.save(expense);
    }

    public void delete(Integer id) {
        expenseRepository.deleteById(id);
    }

    private com.truckms.entity.User currentUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
