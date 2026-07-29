package com.truckms.repository;

import com.truckms.entity.PaymentStatus;
import com.truckms.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findByTripDateBetween(LocalDate start, LocalDate end);

    List<Trip> findByCustomerId(Integer customerId);

    List<Trip> findByTruckId(Integer truckId);

    List<Trip> findByMaterialId(Integer materialId);

    List<Trip> findByPaymentStatus(PaymentStatus status);

    List<Trip> findTop10ByOrderByTripDateDescIdDesc();

    @Query("SELECT COALESCE(SUM(t.customerCharge),0) FROM Trip t WHERE t.tripDate BETWEEN :start AND :end")
    BigDecimal sumIncomeBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(t.expenseAmount),0) FROM Trip t WHERE t.tripDate BETWEEN :start AND :end")
    BigDecimal sumExpenseBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    long countByTripDate(LocalDate date);

    long countByTripDateBetween(LocalDate start, LocalDate end);
}
