package br.com.billscontrol.exception;

import lombok.Builder;

public class ResourceInUseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceInUseException() {
		super("Resource not found");
	}

	@Builder
	public ResourceInUseException(String message, Long resourceId, Class<?> clazz) {
		super(
				(message == null ? "Resource in use" : message) +
				(resourceId == null ? "" : " - Id " + resourceId) + 
				(clazz == null ? "" : " - " + clazz.getName())
		);
	}
	
}
