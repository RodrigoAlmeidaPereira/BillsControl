package br.com.billscontrol.api.financialcontrol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialControlRepository extends JpaRepository<FinancialControl, Long> {

    Page<FinancialControl> findAllByOwnerId(Pageable pageable, Long ownerId);

}
