package com.truckms.controller;

import com.truckms.dto.TruckRequest;
import com.truckms.entity.Truck;
import com.truckms.service.TruckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trucks")
@RequiredArgsConstructor
public class TruckController {

    private final TruckService truckService;

    @GetMapping
    public List<Truck> getAll() {
        return truckService.findAll();
    }

    @GetMapping("/{id}")
    public Truck getById(@PathVariable Integer id) {
        return truckService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Truck> create(@Valid @RequestBody TruckRequest req) {
        return ResponseEntity.ok(truckService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Truck> update(@PathVariable Integer id, @Valid @RequestBody TruckRequest req) {
        return ResponseEntity.ok(truckService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        truckService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
