package br.com.billscontrol.api.bank.rest;

import br.com.billscontrol.api.bank.Bank;
import br.com.billscontrol.api.bank.BankService;
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
@RequestMapping("/banks/{userId}/{financialControlId}")
@AllArgsConstructor
public class BankResource {
	
	private BankService bankService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<Bank>> getAll(
			PagedResourcesAssembler<Bank> assembler,
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
		
		PagedResources<Resource<Bank>> resource = assembler
				.toResource(bankService.findAll(PageRequest.of(page, size), financialControlId),
						bank -> this.toResource(userId, financialControlId, bank));
		
		resource.add(linkTo(methodOn(BankResource.class)
				.getAll(assembler, userId, financialControlId, page, size)).withRel("banks"));
		
		return resource;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Resource<Bank>> getOne(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return bankService.findById(id)
			.map(bank -> this.toResource(userId, financialControlId, bank))
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Bank>> create() {
		Bank bank = Bank.builder().id(1l).name("TEste").description("TESTE").build();

		return ResponseEntity.ok(toResource(1l, 1l, bankService.save(bank)));
		
	}
	
	private Resource<Bank> toResource(Long userId, Long financialControlId, Bank bank) {
		return new Resource<>(bank, linkTo(
				methodOn(BankResource.class).getOne(userId, financialControlId, bank.getId())).withSelfRel());
	}
	
}
