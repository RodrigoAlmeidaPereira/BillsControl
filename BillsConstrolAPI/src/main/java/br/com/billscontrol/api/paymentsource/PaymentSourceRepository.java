package br.com.billscontrol.api.paymentsource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSourceRepository extends JpaRepository<PaymentSource, Long> {
}
