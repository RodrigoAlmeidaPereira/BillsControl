package br.com.billscontrol.api.account;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

	Account save(Account category);
	Optional<Account> findById(Long id);
	Account update(Account category);
    Page<Account> findAll(Pageable pageable);
	boolean isEmpty();
			
}
