package com.truckms.controller;

import com.truckms.dto.ExpenseRequest;
import com.truckms.entity.Expense;
import com.truckms.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        if (start != null && end != null) {
            return expenseService.findByDateRange(start, end);
        }
        return expenseService.findAll();
    }

    @PostMapping
    public ResponseEntity<Expense> create(@Valid @RequestBody ExpenseRequest req) {
        return ResponseEntity.ok(expenseService.create(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
