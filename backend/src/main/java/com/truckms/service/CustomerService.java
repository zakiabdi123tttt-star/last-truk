package com.truckms.service;

import com.truckms.dto.CustomerRequest;
import com.truckms.entity.Customer;
import com.truckms.repository.CustomerRepository;
import com.truckms.repository.PaymentRepository;
import com.truckms.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;
    private final PaymentRepository paymentRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> search(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Macmiil lama helin: " + id));
    }

    public Customer create(CustomerRequest req) {
        Customer c = Customer.builder()
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .address(req.getAddress())
                .build();
        return customerRepository.save(c);
    }

    public Customer update(Integer id, CustomerRequest req) {
        Customer c = findById(id);
        c.setName(req.getName());
        c.setPhoneNumber(req.getPhoneNumber());
        c.setAddress(req.getAddress());
        return customerRepository.save(c);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    // Wadarta trips, lacagaha, iyo Payment History ee macmiilkan
    public Map<String, Object> getCustomerSummary(Integer customerId) {
        var trips = tripRepository.findByCustomerId(customerId);
        BigDecimal totalAmount = trips.stream()
                .map(t -> t.getCustomerCharge() != null ? t.getCustomerCharge() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal paidAmount = trips.stream()
                .map(t -> t.getAmountPaid() != null ? t.getAmountPaid() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal remaining = totalAmount.subtract(paidAmount);

        return Map.of(
                "totalTrips", trips.size(),
                "totalAmount", totalAmount,
                "paidAmount", paidAmount,
                "remainingBalance", remaining,
                "paymentHistory", paymentRepository.findByCustomerIdOrderByPaymentDateDesc(customerId)
        );
    }
}
