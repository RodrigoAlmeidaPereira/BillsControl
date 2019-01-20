package br.com.billscontrol.api.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

	Category save(Category category);
	Optional<Category> findById(Long id);
	Category update(Category category);
	void delete(Category category);
    Page<Category> findAll(Pageable pageable);
	Page<Category> findAll(Pageable pageable, Long financialControlId);
	boolean isEmpty();
	Category toEntity(CategoryVO vo);
	CategoryVO toVO(Category entity);
			
}
