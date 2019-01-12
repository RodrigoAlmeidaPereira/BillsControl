package br.com.billscontrol.exception;

import lombok.Builder;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
		super("Resource not found");
	}
	
	@Builder
	public ResourceNotFoundException(String message, Long resourceId, Class<?> clazz) {
		super(
				(message == null ? "Resource not found" : message) + 
				(resourceId == null ? "" : " - Id " + resourceId) + 
				(clazz == null ? "" : " - " + clazz.getName())
		);
	}
	
}
