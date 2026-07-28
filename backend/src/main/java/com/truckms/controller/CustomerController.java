package com.truckms.controller;

import com.truckms.dto.CustomerRequest;
import com.truckms.entity.Customer;
import com.truckms.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAll(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return customerService.search(search);
        }
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Integer id) {
        return customerService.findById(id);
    }

    @GetMapping("/{id}/summary")
    public Map<String, Object> getSummary(@PathVariable Integer id) {
        return customerService.getCustomerSummary(id);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequest req) {
        return ResponseEntity.ok(customerService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Integer id, @Valid @RequestBody CustomerRequest req) {
        return ResponseEntity.ok(customerService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
