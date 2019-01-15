package br.com.billscontrol.api.bank;

import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

	private BankRepository repository;
	
	@Override
	public Bank save(Bank bank) {
		return this.repository.save(bank);
	}

	@Override
	public Bank update(Bank bank) {

		if (!repository.existsById(bank.getId())) {
			throw ResourceNotFoundException.builder().resourceId(bank.getId()).build();
		}

		return this.save(bank);
	}

	@Override
	public Optional<Bank> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Page<Bank> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

}
