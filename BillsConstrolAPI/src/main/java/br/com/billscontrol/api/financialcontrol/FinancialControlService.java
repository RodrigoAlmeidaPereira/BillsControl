package br.com.billscontrol.api.financialcontrol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FinancialControlService {

	FinancialControl save(FinancialControl entity);
	Optional<FinancialControl> findById(Long id);
	FinancialControl update(FinancialControl entity);
    Page<FinancialControl> findAll(Pageable pageable);
	boolean isEmpty();

	FinancialControl toEntity(FinancialControlVO vo);
	FinancialControlVO toVO(FinancialControl entity);
			
}
