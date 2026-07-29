package com.truckms.controller;

import com.truckms.dto.PaymentRequest;
import com.truckms.entity.Payment;
import com.truckms.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/trip/{tripId}")
    public List<Payment> getByTrip(@PathVariable Integer tripId) {
        return paymentService.findByTrip(tripId);
    }

    @PostMapping
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentRequest req) {
        return ResponseEntity.ok(paymentService.create(req));
    }
}
