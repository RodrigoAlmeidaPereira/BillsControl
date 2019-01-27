package br.com.billscontrol.api.bank;

import br.com.billscontrol.api.financialcontrol.FinancialControlService;
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
	private FinancialControlService financialService;
	
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
		if (id == null) {
			return Optional.empty();
		}

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

	@Override
	public Page<Bank> findAll(Pageable pageable, Long financialControlId) {
		return repository.findAllByFinancialControlId(pageable,financialControlId);
	}

	@Override
	public void delete(Long id) {
		Bank entity = findById(id)
				.orElseThrow(() -> ResourceNotFoundException.builder()
				.resourceId(id)
				.clazz(Bank.class).build());

		repository.delete(entity);
	}

	@Override
	public Bank toEntity(BankVO vo) {

		if (vo == null) {
			return null;
		}

		return findById(vo.getId())
				.orElse(Bank.builder()
					.name(vo.getName())
					.description(vo.getDescription())
					.build());
	}

	@Override
	public BankVO toVO(Bank entity) {

		if (entity == null) {
			return null;
		}

		return BankVO.builder()
				.id(entity.getId())
				.description(entity.getDescription())
				.name(entity.getName())
				.financialControlVO(financialService.toVO(entity.getFinancialControl()))
				.build();
	}
}
