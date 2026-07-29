package com.truckms.repository;

import com.truckms.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Integer> {
    boolean existsByTruckNumber(String truckNumber);
}
