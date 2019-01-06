package br.com.billscontrol.api.transaction;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

	Transaction save(Transaction transaction);
	Optional<Transaction> findById(Long id);
	Transaction update(Transaction transaction);
    Page<Transaction> findAll(Pageable pageable);
	boolean isEmpty();
			
}
