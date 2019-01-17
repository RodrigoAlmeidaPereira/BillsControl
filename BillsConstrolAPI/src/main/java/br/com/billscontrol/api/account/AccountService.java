package br.com.billscontrol.api.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {

	Account save(Account category);
	Optional<Account> findById(Long id);
	Account update(Account category);
	Page<Account> findAll(Pageable pageable);
	Page<Account> findAll(Pageable pageable, Long financialControlId);
	boolean isEmpty();
			
}
