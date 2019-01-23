package br.com.billscontrol.api.bank.rest;

import br.com.billscontrol.api.bank.Bank;
import br.com.billscontrol.api.bank.BankService;
import br.com.billscontrol.api.bank.BankVO;
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
@RequestMapping("/banks")
@AllArgsConstructor
public class BankResource {

	private BankService service;
	private FinancialControlService financialControlService;

	@GetMapping(path = "/{financialControlId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<BankVO>> getAll(
			PagedResourcesAssembler<BankVO> assembler,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer size,
			@RequestParam(defaultValue = "name", required = false) String order,
			@RequestParam(defaultValue = "ASC", required = false) String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), order);

		Page<BankVO> vos = service.findAll(pageRequest, financialControlId)
				.map(service::toVO);

		PagedResources<Resource<BankVO>> resource = assembler
				.toResource(vos, vo -> this.toResource(financialControlId, vo));

		resource.add(linkTo(methodOn(BankResource.class)
				.getAll(assembler, financialControlId, page, size, order, direction))
				.withRel("categories"));

		return resource;
	}

	@GetMapping(path = "/{financialControlId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Resource<BankVO>> getOne(
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return service.findById(id)
				.map(bank -> service.toVO(bank))
				.map(vo -> this.toResource(financialControlId, vo))
				.map(ResponseEntity::ok)
				.orElse(null);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<BankVO>> create(@Valid @RequestBody BankVO vo) {

		Bank entity = service.toEntity(vo);

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
	ResponseEntity<Resource<BankVO>> update(
			@PathVariable Long id,
			@Valid @RequestBody BankVO vo) {

		Bank entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bank not found", id, Bank.class));

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
	ResponseEntity<Resource<BankVO>> delete(
			@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();

	}


	private Resource<BankVO> toResource(Long financialControlId, BankVO vo) {
		return new Resource<>(vo, linkTo(
				methodOn(BankResource.class).getOne(financialControlId, vo.getId())).withSelfRel());
	}
	
}
