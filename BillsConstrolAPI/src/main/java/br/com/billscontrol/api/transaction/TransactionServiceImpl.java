package br.com.billscontrol.api.transaction;

import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private TransactionRepository repository;
	
	@Override
	public Transaction save(Transaction transaction) {
		return this.repository.save(transaction);
	}
	
	@Override
	public Transaction update(Transaction transaction) {
		
		if (!repository.existsById(transaction.getId())) {
			throw new ResourceNotFoundException();
		} 

		return this.save(transaction);
	}
	
	@Override
	public Optional<Transaction> findById(Long id) {		
		return repository.findById(id);
	}
	
	@Override
	public Page<Transaction> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

	@Override
	public Page<Transaction> findAll(Pageable pageable, Long financialControlId, TransactionStatus transactionStatus) {
		return repository.findAllByFinancialControlIdAndTransactionStatus(pageable, financialControlId, transactionStatus);
	}
}
