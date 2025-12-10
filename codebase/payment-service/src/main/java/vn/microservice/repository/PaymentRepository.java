package vn.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.microservice.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(String orderId);
}

