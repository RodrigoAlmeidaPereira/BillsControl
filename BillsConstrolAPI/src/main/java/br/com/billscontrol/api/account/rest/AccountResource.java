package br.com.billscontrol.api.account.rest;

import br.com.billscontrol.api.account.Account;
import br.com.billscontrol.api.account.AccountService;
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
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountResource {
	
	private final AccountService accountService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<Account>> getAll(
			PagedResourcesAssembler<Account> assembler,
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer size) {

		PagedResources<Resource<Account>> resource = assembler
				.toResource(accountService.findAll(PageRequest.of(page, size), financialControlId),
						account -> this.toResource(userId, financialControlId, account));

		resource.add(linkTo(methodOn(AccountResource.class)
				.getAll(assembler, userId, financialControlId, page, size)).withRel("accounts"));

		return resource;
	}

	@GetMapping("/{id}")
	ResponseEntity<Resource<Account>> getOne(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return accountService.findById(id)
				.map(account -> this.toResource(userId, financialControlId, account))
				.map(ResponseEntity::ok)
				.orElse(null);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Account>> create() {
		Account account = Account.builder().id(1l).name("TEste").description("TESTE").build();

		return ResponseEntity.ok(toResource(1l, 1l, accountService.save(account)));

	}

	private Resource<Account> toResource(Long userId, Long financialControlId, Account account) {
		return new Resource<>(account, linkTo(
				methodOn(AccountResource.class).getOne(userId, financialControlId, account.getId())).withSelfRel());
	}

}
