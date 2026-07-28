package com.truckms.service;

import com.truckms.dto.MaterialRequest;
import com.truckms.entity.Material;
import com.truckms.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public List<Material> findAllActive() {
        return materialRepository.findByActiveTrue();
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public Material create(MaterialRequest req) {
        if (materialRepository.existsByNameIgnoreCase(req.getName())) {
            throw new RuntimeException("Material-kan horeba wuu jiraa");
        }
        return materialRepository.save(Material.builder().name(req.getName()).build());
    }

    public Material update(Integer id, MaterialRequest req) {
        Material m = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material lama helin: " + id));
        m.setName(req.getName());
        return materialRepository.save(m);
    }

    public void deactivate(Integer id) {
        Material m = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material lama helin: " + id));
        m.setActive(false);
        materialRepository.save(m);
    }
}
