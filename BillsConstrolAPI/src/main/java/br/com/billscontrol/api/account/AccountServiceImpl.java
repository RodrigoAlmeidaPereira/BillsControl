package br.com.billscontrol.api.account;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private AccountRepository repository;
	
	@Override
	public Account save(Account account) {
		return this.repository.save(account);
	}
	
	@Override
	public Account update(Account account) {
		
		if (!repository.existsById(account.getId())) {
			throw new ResourceNotFoundException();
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

}
