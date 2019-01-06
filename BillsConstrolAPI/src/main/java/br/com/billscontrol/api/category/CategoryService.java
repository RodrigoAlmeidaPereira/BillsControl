package br.com.billscontrol.api.category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

	Category save(Category category);
	Optional<Category> findById(Long id);
	Category update(Category category);
    Page<Category> findAll(Pageable pageable);
	boolean isEmpty();
			
}
