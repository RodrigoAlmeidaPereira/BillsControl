package br.com.billscontrol.api.paymentsource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSourceRepository extends JpaRepository<PaymentSource, Long> {

    Page<PaymentSource> findAllByFinancialControlId(Pageable pageable, Long financialControlId);
}
