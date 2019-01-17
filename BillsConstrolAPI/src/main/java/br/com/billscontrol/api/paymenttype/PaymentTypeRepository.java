package br.com.billscontrol.api.paymenttype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    Page<PaymentType> findAllByFinancialControlId(Pageable pageable, Long financialControlId);

}
