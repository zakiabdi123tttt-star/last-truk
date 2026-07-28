package com.truckms.service;

import com.truckms.dto.PaymentRequest;
import com.truckms.entity.PaymentStatus;
import com.truckms.entity.Trip;
import com.truckms.entity.Payment;
import com.truckms.repository.PaymentRepository;
import com.truckms.repository.TripRepository;
import com.truckms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public List<Payment> findByTrip(Integer tripId) {
        return paymentRepository.findByTripId(tripId);
    }

    // Marka payment cusub la geliyo, Trip-ka waa la cusboonaysiiyaa
    // (Amount Paid + Payment Status - sida Remaining Balance loo xisaabiyo si sax ah)
    public Payment create(PaymentRequest req) {
        Trip trip = tripRepository.findById(req.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip lama helin"));

        Payment payment = Payment.builder()
                .trip(trip)
                .customer(trip.getCustomer())
                .amount(req.getAmount())
                .paymentDate(req.getPaymentDate() != null ? req.getPaymentDate() : LocalDate.now())
                .notes(req.getNotes())
                .recordedBy(currentUser())
                .build();
        Payment saved = paymentRepository.save(payment);

        BigDecimal newAmountPaid = trip.getAmountPaid().add(req.getAmount());
        trip.setAmountPaid(newAmountPaid);

        if (newAmountPaid.compareTo(trip.getCustomerCharge()) >= 0) {
            trip.setPaymentStatus(PaymentStatus.PAID);
        } else if (newAmountPaid.compareTo(BigDecimal.ZERO) > 0) {
            trip.setPaymentStatus(PaymentStatus.PARTIAL);
        } else {
            trip.setPaymentStatus(PaymentStatus.UNPAID);
        }
        tripRepository.save(trip);

        return saved;
    }

    private com.truckms.entity.User currentUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
