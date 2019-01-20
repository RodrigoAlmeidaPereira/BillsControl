package br.com.billscontrol.api.category.rest;

import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
import br.com.billscontrol.api.category.CategoryVO;
import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryResource {
	
	private CategoryService service;
	private FinancialControlService financialControlService;

	@GetMapping(path = "/{financialControlId}",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<CategoryVO>> getAll(
			PagedResourcesAssembler<CategoryVO> assembler,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size,
			@RequestParam(defaultValue = "name", required = false) String order,
			@RequestParam(defaultValue = "ASC", required = false) String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), order);

		Page<CategoryVO> vos = service.findAll(pageRequest, financialControlId)
				.map(service::toVO);

		PagedResources<Resource<CategoryVO>> resource = assembler
				.toResource(vos, vo -> this.toResource(financialControlId, vo));
		
		resource.add(linkTo(methodOn(CategoryResource.class)
				.getAll(assembler, financialControlId, page, size, order, direction))
				.withRel("categories"));
		
		return resource;
	}
	
	@GetMapping(path = "/{financialControlId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Resource<CategoryVO>> getOne(
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return service.findById(id)
			.map(category -> service.toVO(category))
			.map(vo -> this.toResource(financialControlId, vo))
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<CategoryVO>> create(@Valid @RequestBody CategoryVO vo) {

		Category entity = service.toEntity(vo);

		entity = entity.toBuilder()
				.financialControl(financialControlService.findById(vo.getFinancialControlVO().getId()).orElse(null))
				.createUser("SYSTEM")
				.createInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(entity.getFinancialControl().getId(), service.toVO(service.save(entity))));
		
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<CategoryVO>> update(
			@PathVariable Long id,
			@Valid @RequestBody CategoryVO vo) {

		Category entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found", id, Category.class));

		entity = entity.toBuilder()
				.name(vo.getName())
				.description(vo.getDescription())
				.lastUpdateUser("SYSTEM")
				.lastUpdateInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(entity.getFinancialControl().getId(), service.toVO(service.update(entity))));

	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<Resource<Category>> delete(
			@PathVariable Long id) {

		Category entity = service.findById(id)
				.orElseThrow(() -> ResourceNotFoundException.builder()
						.resourceId(id)
						.clazz(Category.class).build());

		service.delete(entity);

		return ResponseEntity.noContent().build();

	}


	private Resource<CategoryVO> toResource(Long financialControlId, CategoryVO vo) {
		return new Resource<>(vo, linkTo(
				methodOn(CategoryResource.class).getOne(financialControlId, vo.getId())).withSelfRel());
	}
	
}
