package br.com.billscontrol.api.category.rest;

import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/categories/{userId}/{financialControlId}")
@AllArgsConstructor
public class CategoryResource {
	
	private CategoryService categoryService; 
	
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
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Category>> create() {
		Category category = Category.builder().id(1l).name("TEste").description("TESTE").build();

		return ResponseEntity.ok(toResource(1l, 1l,categoryService.save(category)));
		
	}
	
	private Resource<Category> toResource(Long userId, Long financialControlId, Category category) {
		return new Resource<>(category, linkTo(
				methodOn(CategoryResource.class).getOne(userId, financialControlId, category.getId())).withSelfRel());
	}
	
}
