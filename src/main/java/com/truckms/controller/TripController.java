package com.truckms.controller;

import com.truckms.dto.TripRequest;
import com.truckms.entity.Trip;
import com.truckms.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public List<Trip> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        if (start != null && end != null) {
            return tripService.findByDateRange(start, end);
        }
        return tripService.findAll();
    }

    @GetMapping("/recent")
    public List<Trip> getRecent() {
        return tripService.recent();
    }

    @GetMapping("/{id}")
    public Trip getById(@PathVariable Integer id) {
        return tripService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Trip> create(@Valid @RequestBody TripRequest req) {
        return ResponseEntity.ok(tripService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> update(@PathVariable Integer id, @Valid @RequestBody TripRequest req) {
        return ResponseEntity.ok(tripService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
