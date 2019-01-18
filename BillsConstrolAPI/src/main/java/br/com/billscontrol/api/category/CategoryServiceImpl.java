package br.com.billscontrol.api.category;

import br.com.billscontrol.exception.ResourceInUseException;
import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository repository;
	
	@Override
	public Category save(Category category) {
		return this.repository.save(category);
	}
	
	@Override
	public Category update(Category category) {
		
		if (!repository.existsById(category.getId())) {
			throw new ResourceNotFoundException();
		} 

		return this.save(category);
	}
	
	@Override
	public Optional<Category> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Page<Category> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

	@Override
	public Page<Category> findAll(Pageable pageable, Long financialControlId) {
		return this.repository.findAllByFinancialControlId(pageable, financialControlId);
	}

	@Override
	public void delete(Category category) {
		try {
			repository.delete(category);
		} catch (DataIntegrityViolationException ex) {
			throw ResourceInUseException.builder()
					.resourceId(category.getId()).clazz(Category.class)
					.build();
		}
	}
}
