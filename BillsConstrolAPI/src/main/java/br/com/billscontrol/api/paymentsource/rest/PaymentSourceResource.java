package br.com.billscontrol.api.paymentsource.rest;

import br.com.billscontrol.api.paymentsource.PaymentSource;
import br.com.billscontrol.api.paymentsource.PaymentSourceService;
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
@RequestMapping("/paymentsSource/{userId}/{financialControlId}")
@AllArgsConstructor
public class PaymentSourceResource {
	
	private PaymentSourceService paymentSourceService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<PaymentSource>> getAll(
			PagedResourcesAssembler<PaymentSource> assembler,
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
		
		PagedResources<Resource<PaymentSource>> resource = assembler
				.toResource(paymentSourceService.findAll(PageRequest.of(page, size), financialControlId),
						paymentSource -> this.toResource(userId, financialControlId, paymentSource));
		
		resource.add(linkTo(methodOn(PaymentSourceResource.class)
				.getAll(assembler, userId, financialControlId, page, size)).withRel("categories"));
		
		return resource;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Resource<PaymentSource>> getOne(
			@PathVariable Long userId,
			@PathVariable Long financialControlId,
			@PathVariable Long id) {

		return paymentSourceService.findById(id)
			.map(bank -> this.toResource(userId, financialControlId, bank))
			.map(ResponseEntity::ok)
			.orElse(null);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<PaymentSource>> create() {
		PaymentSource paymentSource = PaymentSource.builder().id(1l).name("TEste").description("TESTE").build();

		return ResponseEntity.ok(toResource(1l, 1l, paymentSourceService.save(paymentSource)));
		
	}
	
	private Resource<PaymentSource> toResource(Long userId, Long financialControlId, PaymentSource paymentSource) {
		return new Resource<>(paymentSource, linkTo(
				methodOn(PaymentSourceResource.class).getOne(userId, financialControlId, paymentSource.getId())).withSelfRel());
	}
	
}
