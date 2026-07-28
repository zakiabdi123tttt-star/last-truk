package com.truckms.repository;

import com.truckms.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findByActiveTrue();
    boolean existsByNameIgnoreCase(String name);
}
