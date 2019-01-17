package br.com.billscontrol.api.account;

import br.com.billscontrol.api.paymentsource.PaymentSourceService;
import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private AccountRepository repository;
	private PaymentSourceService paymentSourceService;

	@Override
	public Account save(Account account) {

		return this.repository.save(account);
	}
	
	@Override
	public Account update(Account account) {
		
		if (!repository.existsById(account.getId())) {
			throw ResourceNotFoundException.builder().resourceId(account.getId()).build();
		} 

		return this.save(account);
	}
	
	@Override
	public Optional<Account> findById(Long id) {		
		return repository.findById(id);
	}
	
	@Override
	public Page<Account> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

	@Override
	public Page<Account> findAll(Pageable pageable, Long financialControlId) {
		return repository.findAllByFinancialControlId(pageable, financialControlId);
	}
}
