package br.com.billscontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		
		StandardError err = StandardError.builder()
				.errorCode(HttpStatus.NOT_FOUND.value())
				.status(HttpStatus.NOT_FOUND.toString())
				.message(e.getMessage())
				.instant(Instant.now())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}


	@ExceptionHandler(ResourceInUseException.class)
	public ResponseEntity<StandardError> resourceInUse(ResourceInUseException e, HttpServletRequest request) {

		StandardError err = StandardError.builder()
				.errorCode(HttpStatus.CONFLICT.value())
				.status(HttpStatus.CONFLICT.toString())
				.message(e.getMessage())
				.instant(Instant.now())
				.build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> dataIntegriry(MethodArgumentNotValidException e, HttpServletRequest request) {


		Collection<FieldMessage> messageErrors = e.getBindingResult().getFieldErrors().stream()
				.map(error -> FieldMessage.builder().message(error.getField()).fieldName(error.getDefaultMessage()).build())
				.collect(Collectors.toList());

		ValidationError err = ValidationError.builder()
				.errorCode(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST.toString())
				.message("Validation error")
				.instant(Instant.now())
				.errors(messageErrors)
				.build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}


}
