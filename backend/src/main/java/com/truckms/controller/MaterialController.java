package com.truckms.controller;

import com.truckms.dto.MaterialRequest;
import com.truckms.entity.Material;
import com.truckms.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public List<Material> getAll() {
        return materialService.findAllActive();
    }

    @GetMapping("/all")
    public List<Material> getAllIncludingInactive() {
        return materialService.findAll();
    }

    @PostMapping
    public ResponseEntity<Material> create(@Valid @RequestBody MaterialRequest req) {
        return ResponseEntity.ok(materialService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> update(@PathVariable Integer id, @Valid @RequestBody MaterialRequest req) {
        return ResponseEntity.ok(materialService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Integer id) {
        materialService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
