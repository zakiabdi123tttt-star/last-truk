package com.truckms.service;

import com.truckms.dto.TripRequest;
import com.truckms.entity.*;
import com.truckms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TruckRepository truckRepository;
    private final MaterialRepository materialRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findById(Integer id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip lama helin: " + id));
    }

    public List<Trip> findByDateRange(LocalDate start, LocalDate end) {
        return tripRepository.findByTripDateBetween(start, end);
    }

    public List<Trip> recent() {
        return tripRepository.findTop10ByOrderByTripDateDescIdDesc();
    }

    public Trip create(TripRequest req) {
        Truck truck = req.getTruckId() != null
                ? truckRepository.findById(req.getTruckId()).orElseThrow(() -> new RuntimeException("Truck lama helin"))
                : null;
        Material material = materialRepository.findById(req.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material lama helin"));
        Customer customer = customerRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Macmiil lama helin"));

        Trip trip = Trip.builder()
                .tripDate(req.getTripDate())
                .truck(truck)
                .material(material)
                .customer(customer)
                .quantity(req.getQuantity())
                .unit(req.getUnit() != null ? req.getUnit() : TripUnit.LOADS)
                .deliveryLocation(req.getDeliveryLocation())
                .customerCharge(req.getCustomerCharge())
                .expenseAmount(req.getExpenseAmount() != null ? req.getExpenseAmount() : BigDecimal.ZERO)
                .paymentStatus(req.getPaymentStatus() != null ? req.getPaymentStatus() : PaymentStatus.UNPAID)
                .amountPaid(req.getAmountPaid() != null ? req.getAmountPaid() : BigDecimal.ZERO)
                .notes(req.getNotes())
                .createdBy(currentUser())
                .build();

        Trip saved = tripRepository.save(trip);
        logAudit("CREATE", saved.getId(), "Trip cusub oo la abuuray");
        return saved;
    }

    public Trip update(Integer id, TripRequest req) {
        Trip trip = findById(id);
        Truck truck = req.getTruckId() != null
                ? truckRepository.findById(req.getTruckId()).orElseThrow(() -> new RuntimeException("Truck lama helin"))
                : null;
        Material material = materialRepository.findById(req.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material lama helin"));
        Customer customer = customerRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Macmiil lama helin"));

        trip.setTripDate(req.getTripDate());
        trip.setTruck(truck);
        trip.setMaterial(material);
        trip.setCustomer(customer);
        trip.setQuantity(req.getQuantity());
        if (req.getUnit() != null) trip.setUnit(req.getUnit());
        trip.setDeliveryLocation(req.getDeliveryLocation());
        trip.setCustomerCharge(req.getCustomerCharge());
        if (req.getExpenseAmount() != null) trip.setExpenseAmount(req.getExpenseAmount());
        if (req.getPaymentStatus() != null) trip.setPaymentStatus(req.getPaymentStatus());
        if (req.getAmountPaid() != null) trip.setAmountPaid(req.getAmountPaid());
        trip.setNotes(req.getNotes());

        Trip saved = tripRepository.save(trip);
        logAudit("UPDATE", saved.getId(), "Trip waa la cusboonaysiiyay");
        return saved;
    }

    public void delete(Integer id) {
        tripRepository.deleteById(id);
        logAudit("DELETE", id, "Trip waa la tirtiray");
    }

    private User currentUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private void logAudit(String action, Integer entityId, String details) {
        auditLogRepository.save(AuditLog.builder()
                .user(currentUser())
                .action(action)
                .entityName("Trip")
                .entityId(entityId)
                .details(details)
                .build());
    }
}
