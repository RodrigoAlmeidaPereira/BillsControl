package br.com.billscontrol.api.bank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Page<Bank> findAllByFinancialControlId(Pageable pageable, Long financialControlId);

}
