package com.truckms.repository;

import com.truckms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByTripId(Integer tripId);
    List<Payment> findByCustomerIdOrderByPaymentDateDesc(Integer customerId);
}
