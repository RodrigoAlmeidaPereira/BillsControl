package br.com.billscontrol.api.bank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankService {

	Bank save(Bank entity);
	Optional<Bank> findById(Long id);
	Bank update(Bank category);
    Page<Bank> findAll(Pageable pageable);
	Page<Bank> findAll(Pageable pageable, Long financialControlId);
	void delete(Long id);
	boolean isEmpty();

	Bank toEntity(BankVO vo);
	BankVO toVO(Bank entity);
			
}