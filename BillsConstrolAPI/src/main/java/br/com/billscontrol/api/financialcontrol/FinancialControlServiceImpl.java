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

		if (id == null) {
			return Optional.empty();
		}

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

	@Override
	public FinancialControl toEntity(FinancialControlVO vo) {

		if (vo == null) {
			return null;
		}

		FinancialControl entity = findById(vo.getId()).orElse(null);

		if (entity != null) {
			entity = entity.toBuilder()
					.name(vo.getName())
					.description(vo.getDescription())
					.build();
		}

		return entity;
	}

	@Override
	public FinancialControlVO toVO(FinancialControl entity) {

		if (entity == null) {
			return null;
		}

		return FinancialControlVO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.build();
	}
}
