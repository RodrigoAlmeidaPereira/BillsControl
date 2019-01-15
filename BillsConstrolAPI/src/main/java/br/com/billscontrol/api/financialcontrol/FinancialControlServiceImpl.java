package br.com.billscontrol.api.financialcontrol;

import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FinancialControlServiceImpl implements FinancialControlService {

	private FinancialControlRepository repository;
	
	@Override
	public FinancialControl save(FinancialControl entity) {
		return this.repository.save(entity);
	}
	
	@Override
	public FinancialControl update(FinancialControl entity) {
		
		if (!repository.existsById(entity.getId())) {
			throw ResourceNotFoundException.builder().resourceId(entity.getId()).build();
		} 

		return this.save(entity);
	}
	
	@Override
	public Optional<FinancialControl> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Page<FinancialControl> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

}