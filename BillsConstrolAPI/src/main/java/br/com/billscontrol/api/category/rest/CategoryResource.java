package br.com.billscontrol.api.category.rest;

import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/categories/{userId}/{financialControlId}")
@AllArgsConstructor
public class CategoryResource {
	
	private CategoryService categoryService;
	private FinancialControlService financialControlService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<Category>> getAll(
			PagedResourcesAssembler<Category> assembler,
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
		
		PagedResources<Resource<Category>> resource = assembler
				.toResource(categoryService.findAll(PageRequest.of(page, size), financialControlId),
						category -> this.toResource(userId, financialControlId, category));
		
		resource.add(linkTo(methodOn(CategoryResource.class)
				.getAll(assembler, userId, financialControlId, page, size)).withRel("categories"));
		
		return resource;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Resource<Category>> getOne(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return categoryService.findById(id)
			.map(category -> this.toResource(userId, financialControlId, category))
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Category>> create(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@RequestBody Category category) {

		Category entity = category.toBuilder()
				.financialControl(financialControlService.findById(financialControlId).orElse(null))
				.createUser("SYSTEM")
				.createInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(userId, financialControlId, categoryService.save(entity)));
		
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Category>> update(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id,
			@RequestBody Category category) {

		Category entity = categoryService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found", id, Category.class));

		entity = entity.toBuilder()
				.name(category.getName())
				.description(category.getDescription())
				.lastUpdateUser("SYSTEM")
				.lastUpdateInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(userId, financialControlId, categoryService.update(entity)));

	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<Resource<Category>> delete(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		Category entity = categoryService.findById(id)
				.orElseThrow(() -> ResourceNotFoundException.builder()
						.resourceId(id)
						.clazz(Category.class).build());

		categoryService.delete(entity);

		return ResponseEntity.noContent().build();

	}


	private Resource<Category> toResource(Long userId, Long financialControlId, Category category) {
		return new Resource<>(category, linkTo(
				methodOn(CategoryResource.class).getOne(userId, financialControlId, category.getId())).withSelfRel());
	}
	
}
