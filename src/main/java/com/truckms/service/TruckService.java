package com.truckms.service;

import com.truckms.dto.TruckRequest;
import com.truckms.entity.Truck;
import com.truckms.entity.TruckStatus;
import com.truckms.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;

    public List<Truck> findAll() {
        return truckRepository.findAll();
    }

    public Truck findById(Integer id) {
        return truckRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Truck lama helin: " + id));
    }

    public Truck create(TruckRequest req) {
        if (truckRepository.existsByTruckNumber(req.getTruckNumber())) {
            throw new RuntimeException("Truck number-kan horeba waa la isticmaalay");
        }
        Truck truck = Truck.builder()
                .truckNumber(req.getTruckNumber())
                .plateNumber(req.getPlateNumber())
                .driverName(req.getDriverName())
                .status(req.getStatus() != null ? req.getStatus() : TruckStatus.ACTIVE)
                .build();
        return truckRepository.save(truck);
    }

    public Truck update(Integer id, TruckRequest req) {
        Truck truck = findById(id);
        truck.setTruckNumber(req.getTruckNumber());
        truck.setPlateNumber(req.getPlateNumber());
        truck.setDriverName(req.getDriverName());
        if (req.getStatus() != null) truck.setStatus(req.getStatus());
        return truckRepository.save(truck);
    }

    public void delete(Integer id) {
        truckRepository.deleteById(id);
    }
}
