package br.com.billscontrol.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		
		StandardError err = StandardError.builder()
				.errorCode(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage())
				.instant(Instant.now())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
