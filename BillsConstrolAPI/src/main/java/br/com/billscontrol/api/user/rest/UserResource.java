package br.com.billscontrol.api.user.rest;

import br.com.billscontrol.api.user.User;
import br.com.billscontrol.api.user.UserService;
import br.com.billscontrol.api.user.UserVO;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserResource {

	private UserService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Resources<Resource<UserVO>> getAll(
			PagedResourcesAssembler<UserVO> assembler,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer size,
			@RequestParam(defaultValue = "name", required = false) String order,
			@RequestParam(defaultValue = "ASC", required = false) String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), order);

		Page<UserVO> vos = service.findAll(pageRequest)
				.map(service::toVO);

		PagedResources<Resource<UserVO>> resource = assembler
				.toResource(vos, this::toResource);

		resource.add(linkTo(methodOn(UserResource.class)
				.getAll(assembler, page, size, order, direction))
				.withRel("user"));

		return resource;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Resource<UserVO>> getOne(
			@PathVariable Long id) {

		return service.findById(id)
				.map(user -> service.toVO(user))
				.map(this::toResource)
				.map(ResponseEntity::ok)
				.orElse(null);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<UserVO>> create(@Valid @RequestBody UserVO vo) {

		User entity = service.toEntity(vo);

		entity = entity.toBuilder()
				.createUser("SYSTEM")
				.createInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(service.toVO(service.save(entity))));

	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<Resource<UserVO>> update(
			@PathVariable Long id,
			@Valid @RequestBody UserVO vo) {

		User entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found", id, User.class));

		entity = entity.toBuilder()
				.name(vo.getName())
				.lastUpdateUser("SYSTEM")
				.lastUpdateInstant(Instant.now())
				.build();

		return ResponseEntity.ok(toResource(service.toVO(service.update(entity))));

	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<Resource<UserVO>> delete(
			@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();

	}


	private Resource<UserVO> toResource(UserVO vo) {
		return new Resource<>(vo, linkTo(
				methodOn(UserResource.class).getOne(vo.getId())).withSelfRel());
	}
	
}
