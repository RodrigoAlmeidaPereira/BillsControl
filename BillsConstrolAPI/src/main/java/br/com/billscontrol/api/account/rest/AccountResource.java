package br.com.billscontrol.api.account.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import br.com.billscontrol.api.account.Account;
import br.com.billscontrol.api.account.AccountService;
import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountResource {
	
	private final AccountService accountService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<Account>> getAll(
			PagedResourcesAssembler<Account> assembler,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
		
		PagedResources<Resource<Account>> resource = assembler
				.toResource(accountService.findAll(PageRequest.of(page, size)), this::toResource);
		
		resource.add(linkTo(methodOn(AccountResource.class)
				.getAll(assembler, page, size)).withRel("a"));
		
		return resource;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Resource<Account>> getOne(@PathVariable Long id) {
		
		return accountService.findById(id)
			.map(this::toResource)
			.map(ResponseEntity::ok)
			.orElseThrow(() -> 
				ResourceNotFoundException.builder()
					.resourceId(id)
					.clazz(Account.class)
					.build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Account>> create(Account account) {
		return ResponseEntity.ok(toResource(accountService.save(account)));
		
	}
	
	private Resource<Account> toResource(Account category) {
		return new Resource<>(category, linkTo(
				methodOn(AccountResource.class).getOne(category.getId())).withSelfRel());
	}

}
