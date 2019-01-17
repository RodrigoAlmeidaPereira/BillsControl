package br.com.billscontrol.api.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionService {

	Transaction save(Transaction transaction);
	Optional<Transaction> findById(Long id);
	Transaction update(Transaction transaction);
    Page<Transaction> findAll(Pageable pageable);
	Page<Transaction> findAll(Pageable pageable, Long financialControlId, TransactionStatus transactionStatus);
	boolean isEmpty();
			
}
