package br.com.billscontrol.api.category.rest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryResource {
	
	private CategoryService categoryService; 
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<Category>> getAll(
			PagedResourcesAssembler<Category> assembler,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
		
		PagedResources<Resource<Category>> resource = assembler
				.toResource(categoryService.findAll(PageRequest.of(page, size)), this::toResource);
		
		resource.add(linkTo(methodOn(CategoryResource.class)
				.getAll(assembler, page, size)).withRel("categories"));
		
		return resource;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Resource<Category>> getOne(@PathVariable Long id) {
		return categoryService.findById(id)
			.map(this::toResource)
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Category>> create() {
		Category category = Category.builder().id(1l).name("TEste").description("TESTE").build();

		return ResponseEntity.ok(toResource(categoryService.save(category)));
		
	}
	
	private Resource<Category> toResource(Category category) {
		return new Resource<>(category, linkTo(
				methodOn(CategoryResource.class).getOne(category.getId())).withSelfRel());
	}
	
}
