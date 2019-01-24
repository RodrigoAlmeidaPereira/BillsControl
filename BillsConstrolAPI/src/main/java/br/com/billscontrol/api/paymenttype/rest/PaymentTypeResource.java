package br.com.billscontrol.api.paymenttype.rest;

import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.api.paymenttype.PaymentType;
import br.com.billscontrol.api.paymenttype.PaymentTypeService;
import br.com.billscontrol.api.paymenttype.PaymentTypeVO;
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
@RequestMapping("/paymentTypes/{userId}/{financialControlId}")
@AllArgsConstructor
 class PaymentTypeResource {

	private PaymentTypeService service;
	private FinancialControlService financialControlService;

	@GetMapping(path = "/{financialControlId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<PaymentTypeVO>> getAll(
			PagedResourcesAssembler<PaymentTypeVO> assembler,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer size,
			@RequestParam(defaultValue = "name", required = false) String order,
			@RequestParam(defaultValue = "ASC", required = false) String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), order);

		Page<PaymentTypeVO> vos = service.findAll(pageRequest, financialControlId)
				.map(service::toVO);

		PagedResources<Resource<PaymentTypeVO>> resource = assembler
				.toResource(vos, vo -> this.toResource(financialControlId, vo));

		resource.add(linkTo(methodOn(PaymentTypeResource.class)
				.getAll(assembler, financialControlId, page, size, order, direction))
				.withRel("categories"));

		return resource;
	}

	@GetMapping(path = "/{financialControlId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Resource<PaymentTypeVO>> getOne(
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return service.findById(id)
				.map(paymentType -> service.toVO(paymentType))
				.map(vo -> this.toResource(financialControlId, vo))
				.map(ResponseEntity::ok)
				.orElse(null);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<PaymentTypeVO>> create(@Valid @RequestBody PaymentTypeVO vo) {

		PaymentType entity = service.toEntity(vo);

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
	ResponseEntity<Resource<PaymentTypeVO>> update(
			@PathVariable Long id,
			@Valid @RequestBody PaymentTypeVO vo) {

		PaymentType entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PaymentType not found", id, PaymentType.class));

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
	ResponseEntity<Resource<PaymentTypeVO>> delete(
			@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();

	}


	private Resource<PaymentTypeVO> toResource(Long financialControlId, PaymentTypeVO vo) {
		return new Resource<>(vo, linkTo(
				methodOn(PaymentTypeResource.class).getOne(financialControlId, vo.getId())).withSelfRel());
	}


}
