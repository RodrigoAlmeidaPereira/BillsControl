package br.com.billscontrol.api.transaction.rest;

import br.com.billscontrol.api.transaction.Transaction;
import br.com.billscontrol.api.transaction.TransactionService;
import br.com.billscontrol.api.transaction.TransactionStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/transactions/{userId}/{financialControlId}")
@AllArgsConstructor
 class TransactionResource {
	
	private TransactionService transactionService;
	
	@GetMapping(path = "/{transactionStatus}",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<Transaction>> getAll(
			PagedResourcesAssembler<Transaction> assembler,
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable String transactionStatus,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {

		Page<Transaction> transactions = transactionService.findAll(PageRequest.of(page, size), financialControlId,
				TransactionStatus.getByStatus(transactionStatus));

		PagedResources<Resource<Transaction>> resource = assembler.toResource(transactions,
						transaction -> this.toResource(userId, financialControlId, transaction));
		
		resource.add(linkTo(methodOn(TransactionResource.class)
				.getAll(assembler, userId, financialControlId, transactionStatus,page, size)).withRel("transactions"));
		
		return resource;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Resource<Transaction>> getOne(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return transactionService.findById(id)
			.map(transaction -> this.toResource(userId, financialControlId, transaction))
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<Transaction>> create() {
		Transaction transaction = Transaction.builder().id(1L).build();

		return ResponseEntity.ok(toResource(1L, 1L, transactionService.save(transaction)));
		
	}
	
	private Resource<Transaction> toResource(Long userId, Long financialControlId, Transaction transaction) {
		return new Resource<>(transaction, linkTo(
				methodOn(TransactionResource.class).getOne(userId, financialControlId, transaction.getId())).withSelfRel());
	}
	
}
