package br.com.billscontrol.api.financialcontrol.rest;

import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.api.financialcontrol.FinancialControlVO;
import br.com.billscontrol.api.user.UserService;
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
@RequestMapping("/financialControl")
@AllArgsConstructor
public class FinancialControlResource {
	
	private FinancialControlService service;
	private UserService userService;

	@GetMapping(path = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<FinancialControlVO>> getAll(
			PagedResourcesAssembler<FinancialControlVO> assembler,
			@PathVariable Long userId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size,
			@RequestParam(defaultValue = "name", required = false) String order,
			@RequestParam(defaultValue = "ASC", required = false) String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), order);

		Page<FinancialControlVO> vos = service.findAll(pageRequest, userId)
				.map(service::toVO);

		PagedResources<Resource<FinancialControlVO>> resource = assembler
				.toResource(vos, vo -> this.toResource(userId, vo));
		
		resource.add(linkTo(methodOn(FinancialControlResource.class)
				.getAll(assembler, userId, page, size, order, direction))
				.withRel("financialControl"));
		
		return resource;
	}
	
	@GetMapping(path = "/{userId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Resource<FinancialControlVO>> getOne(
			@PathVariable Long userId,
			@PathVariable Long id) {

		return service.findById(id)
			.map(financialControl -> service.toVO(financialControl))
			.map(vo -> this.toResource(userId, vo))
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<FinancialControlVO>> create(@Valid @RequestBody FinancialControlVO vo) {

		FinancialControl entity = service.toEntity(vo);

		entity = entity.toBuilder()
				.owner(userService.findById(vo.getUserVO().getId()).orElse(null))
				.createUser("SYSTEM")
				.createInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(entity.getId(), service.toVO(service.save(entity))));
		
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<FinancialControlVO>> update(
			@PathVariable Long id,
			@Valid @RequestBody FinancialControlVO vo) {

		FinancialControl entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FinancialControl not found", id, FinancialControl.class));

		entity = entity.toBuilder()
				.name(vo.getName())
				.description(vo.getDescription())
				.lastUpdateUser("SYSTEM")
				.lastUpdateInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(entity.getId(), service.toVO(service.update(entity))));

	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<Resource<FinancialControlVO>> delete(
			@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();

	}


	private Resource<FinancialControlVO> toResource(Long financialControlId, FinancialControlVO vo) {
		return new Resource<>(vo, linkTo(
				methodOn(FinancialControlResource.class).getOne(financialControlId, vo.getId())).withSelfRel());
	}
	
}
